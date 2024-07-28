package Core;

import Actors.*;
import Components.*;
import Util.AABB;
import Util.GameMapLoader;
import Util.Position2D;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
public class GameEngine
{
    private final Dimension screenSize;
    private final String currentMap;
    // Game Objects
    private Player player;
    private PlayerInputComponent playerInputComponent;

    // Concrete Types of the game
    //

    private ArrayList<Wall> walls;
    private ArrayList<Enemy> enemies;
    private ArrayList<PowerUp> powerUps;
    private ArrayList<Bullet> bulletsInCirculation;
    // Add extra components if you like
    private ArrayList<IRealTimeComponent> playerComponents;
    private ArrayList<IRealTimeComponent> wallComponents;
    private ArrayList<IRealTimeComponent> enemyComponents;
    private ArrayList<IRealTimeComponent> powerupComponents;
    private ArrayList<IRealTimeComponent> bulletComponents;
    private ArrayList<IRealTimeComponent> miscComponents;
    private void ResetGame(){
        bulletsInCirculation.clear();
        walls.clear();
        enemies.clear();
        powerUps.clear();

        GameMapLoader map = new GameMapLoader(screenSize);
        boolean mapOK = map.loadMap(this.currentMap);

        if(!mapOK)
        {
            System.out.println("Util.Map Load Failed!");
            System.exit(1);
        }
        // TODO: Add code if your design requires so
        //
        // gonna change do not forget to change this part
        //
        // initializing components
        this.playerComponents= new ArrayList<IRealTimeComponent>();
        this.wallComponents= new ArrayList<IRealTimeComponent>();
        this.bulletComponents=new ArrayList<IRealTimeComponent>();
        this.enemyComponents=new ArrayList<IRealTimeComponent>();
        this.powerupComponents=new ArrayList<IRealTimeComponent>();

        //
        //creation of the enemies
        //
        ArrayList<AABB> AABBStatEnemies= map.getLoadedEnemyStationaryAABBs();
        for (int i=0; i<AABBStatEnemies.size(); i++){
            Enemy tEnemy=new Enemy(AABBStatEnemies.get(i).getPos(),AABBStatEnemies.get(i).getSizeX(),AABBStatEnemies.get(i).getSizeY(),AABBStatEnemies.get(i).getCenter(),this, Enemy.enemyType.STATIONARY);
            this.enemies.add(tEnemy);
        }
        ArrayList<AABB> AABBXEnemies= map.getLoadedEnemyXAABBs();
        for (int i=0; i<AABBXEnemies.size(); i++){
            Enemy tEnemy=new Enemy(AABBXEnemies.get(i).getPos(),AABBXEnemies.get(i).getSizeX(),AABBXEnemies.get(i).getSizeY(),AABBXEnemies.get(i).getCenter(),this,Enemy.enemyType.HORIZONTAL);
            this.enemies.add(tEnemy);
        }
        ArrayList<AABB> AABBYEnemies= map.getLoadedEnemyYAABBs();
        for (int i=0; i<AABBYEnemies.size(); i++){
            Enemy tEnemy=new Enemy(AABBYEnemies.get(i).getPos(),AABBYEnemies.get(i).getSizeX(),AABBYEnemies.get(i).getSizeY(),AABBYEnemies.get(i).getCenter(),this,Enemy.enemyType.VERTICAL);
            this.enemies.add(tEnemy);
        }
        //
        // powerups
        //
        ArrayList<AABB> AABBPowerups= map.getLoadedPowerUpAABBs();
        for (int i=0; i<AABBPowerups.size(); i++){
            PowerUp tPowerup=new PowerUp(AABBPowerups.get(i).getPos(),AABBPowerups.get(i).getSizeX(),AABBPowerups.get(i).getSizeY(),this);
            this.powerUps.add(tPowerup);
        }
        //
        // player creation
        //
        this.player=(new Player(map.getLoadedPlayerAABB().getPos(),map.getLoadedPlayerAABB().getSizeX(),map.getLoadedPlayerAABB().getSizeY(),this));

        //
        // walls
        //
        ArrayList<AABB> AABBWalls=map.getLoadedWallAABBs();
        for (int i=0; i<AABBWalls.size(); i++){
            Wall tWall=new Wall(AABBWalls.get(i).getPos(),AABBWalls.get(i).getSizeX(),AABBWalls.get(i).getSizeY(),AABBWalls.get(i).getCenter(),this);
            this.walls.add(tWall);
        }

        // player movement
        playerComponents.add(new PlayerInputComponent());
        // player power up interaction
        CollisionComponent collPublisherofPlayer=new CollisionComponent();
        collPublisherofPlayer.registerSubscriber(this.powerUps);
        playerComponents.add(collPublisherofPlayer);
        this.player.addComponents(playerComponents);



        // wall components only interaction with player and enemy
        CollisionComponent collisionComponentWallPlayer=new CollisionComponent<>();
        CollisionComponent collisionComponentWallEnemy=new CollisionComponent<>();

        ArrayList<ICollisionListener> subscribersOfWallPlayer=new ArrayList<ICollisionListener>();

        subscribersOfWallPlayer.add(this.player); // array for player
        collisionComponentWallEnemy.registerSubscriber(this.enemies); // wall enemy interaction collision component
        collisionComponentWallPlayer.registerSubscriber(subscribersOfWallPlayer); // wall player interaction collision component

        this.wallComponents.add(collisionComponentWallEnemy);
        this.wallComponents.add(collisionComponentWallPlayer);


        this.walls.forEach(actor->actor.addComponents(this.wallComponents));

        // enemy components only with player
        CollisionComponent collisionComponentEnemyPlayer=new CollisionComponent<>();
        //reuse from above
        ArrayList<ICollisionListener> subscribersOfEnemyPlayer=new ArrayList<ICollisionListener>();
        subscribersOfEnemyPlayer.add(this.player);
        collisionComponentEnemyPlayer.registerSubscriber(subscribersOfEnemyPlayer);
        this.enemyComponents.add(collisionComponentEnemyPlayer);
        this.enemies.forEach(actor->actor.addComponents(this.enemyComponents));



        // bullet components only with enemy
        CollisionComponent collisionComponentBulletEnemy=new CollisionComponent<>();
        collisionComponentBulletEnemy.registerSubscriber(this.enemies);
        this.bulletComponents.add(collisionComponentBulletEnemy);
        this.bulletsInCirculation.forEach(actor->actor.addComponents(this.bulletComponents));

    }
    public ArrayList<IRealTimeComponent> getBulletComponents(){
        return this.bulletComponents;
    }
    public ArrayList<Bullet> getBullets(){
        return this.bulletsInCirculation;
    }

    public GameEngine(String mapFilePath, Dimension screenSize){
        this.currentMap = mapFilePath;
        this.screenSize = screenSize;

        this.walls = new ArrayList<Wall>();
        this.enemies = new ArrayList<Enemy>();
        this.powerUps = new ArrayList<PowerUp>();
        this.bulletsInCirculation = new ArrayList<Bullet>();
        this.miscComponents = new ArrayList<IRealTimeComponent>();

        // TODO: Add code if your design requires so
        this.playerComponents= new ArrayList<IRealTimeComponent>();
        this.wallComponents= new ArrayList<IRealTimeComponent>();
        this.enemyComponents= new ArrayList<IRealTimeComponent>();
        this.powerupComponents= new ArrayList<IRealTimeComponent>();
        this.bulletComponents= new ArrayList<IRealTimeComponent>();
        ResetGame();
    }

    public synchronized void update(float deltaT, Graphics2D currentDrawBuffer)
    {
        // ==================================== //
        // YOU SHOULD NOT CHANGE THIS FUNCTION  //
        // ============================================= //
        // THIS SHOULD ALREADY DOES EVERYTHING YOU NEED  //
        // ============================================= //
        // You can still change it though with a penalty.

        // Do update first
        walls.forEach(actor -> actor.update(deltaT, currentDrawBuffer));
        enemies.forEach(actor -> actor.update(deltaT, currentDrawBuffer));
        powerUps.forEach(actor -> actor.update(deltaT, currentDrawBuffer));
        bulletsInCirculation.forEach(actor-> actor.update(deltaT, currentDrawBuffer));
        player.update(deltaT, currentDrawBuffer);
        miscComponents.forEach(c -> c.update(deltaT));
        // Now stuff would die etc. check the states and delete
        enemies.removeIf(actor -> actor.isDead());
        powerUps.removeIf(actor -> actor.isDead());
        bulletsInCirculation.removeIf(actor -> actor.isDead());
        // If player dies game is over reset the system
        if(player.isDead())
        {
            ResetGame();
        }
        // If there are no power-ups left,
        // Player won the game!, still reset..
        if(powerUps.isEmpty())
        {
            ResetGame();
        }
        // And the game goes on forever...
    }
}
