package poo.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

// CLASE Camion QUE HEREDA DE CLASE Object
public class Camion extends Object {

    // CONSTRUCTORES SOBRECARGADOS
    public Camion(int x, int y, int w, int h, Texture img){
        super(x, y, w, h, img);
    }

    public Camion(int x, int y, Texture img){
        super(x, y, 62, 147, img);
    }

    protected void finalize(){
        super.finalize();
        System.out.println("Camion destroyed");
    }

    // SOBRESCRIBIR METODO VIRTUAL DE CLASE Object, PARA SER USADO EN POLIMORFISMO
    @Override
    public void acelera(){
        this.y -= 500 * Gdx.graphics.getDeltaTime();
    }

    public void chocar(Object a) {
        a.x = 390;
        a.velocidad = 0f;
       System.out.println("a destroyed");
    }
}
