package poo.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

// CLASE PADRE QUE HEREDA DE CLASE Rectangle
public abstract class Object extends Rectangle{

    public Texture image;
    protected static float velocidad = 0f;
    protected static float velocidadLimite = 5f;
    protected static int score=0;
    protected static int gas = 100;

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
    public int getVelocidad(){
        return Math.round(velocidad * 100);
    }
    public int getPuntos(){
        return score;
    }
    public int getGasolina(){
        return gas;
    }
    public void actualizaGasolina(){
        gas -= 5;
        if (gas < 0) {
            gas = 0;
            velocidad --;
            if(velocidad < 0) {
                velocidad = 0;

            }
            velocidadLimite = 0;

        }
    }
    public boolean sinGasolina(){
        return gas == 0;
    }
    public void acelera() {}
    public void frena() {}
    public void moverDerecha() {}
    public void moverIzquierda() {}
    public int chocar(Object a, Object b) {return 0;}
    public void addScore() {}

}
