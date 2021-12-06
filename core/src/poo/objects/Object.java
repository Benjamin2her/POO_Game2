package poo.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

// CLASE PADRE QUE HEREDA DE CLASE Rectangle
public abstract class Object extends Rectangle{

    public Texture image;
    protected float velocidad = 0f;
    protected float velocidadLimite = 5f;
    // CONSTRUCTOR
    public Object(int x, int y, int w, int h, Texture img){

        this.x = x;
        this.y = y;
        this.width  = w;
        this.height = h;
        this.image  = img;
    }

    protected void finalize(){
        System.out.println("Object destroyed");
    }

    public void acelera() {}
    public void frena() {}
    public void moverDerecha() {}
    public void moverIzquierda() {}
}
