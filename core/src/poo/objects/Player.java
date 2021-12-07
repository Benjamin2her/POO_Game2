package poo.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

// CLASE Player QUE HEREDA DE CLASE Object
public class Player extends Object{

    // CONSTRUCTOR
    public Player(int x, int y, Texture img){
        super(x, y, 64, 129, img);
    }

    public void finalize(){
        super.finalize();
        System.out.println("Player destroyed");
    }
    @Override

    public void moverIzquierda(){
        this.x -= 2000  * Gdx.graphics.getDeltaTime();
        if(this.x < 140) this.x = 140;
    }
    public void moverDerecha(){
        this.x += 2000 * Gdx.graphics.getDeltaTime();
        if(this.x > 650) this.x = 650;
    }

    public void giraDerecha(){
        this.x += 400 * Gdx.graphics.getDeltaTime();
        if(this.x > 650) this.x = 650;
    }

    public void giraIzquierda(){
        this.x -= 400 * Gdx.graphics.getDeltaTime();
        if(this.x < 140) this.x = 140;
    }

}
