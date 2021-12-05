package poo.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

// CLASE Drop QUE HEREDA DE CLASE Object
public class Drop extends Object {

    // CONSTRUCTORES SOBRECARGADOS
    public Drop(int x, int y, int w, int h, Texture img){
        super(x, y, w, h, img);
    }

    public Drop(int x, int y, Texture img){
        super(x, y, 64, 64, img);
    }


    protected void finalize(){
        super.finalize();
        System.out.println("Drop destroyed");
    }

    // SOBRESCRIBIR METODO VIRTUAL DE CLASE Object, PARA SER USADO EN POLIMORFISMO
    @Override
    public void speed(){
        this.y -= 400 * Gdx.graphics.getDeltaTime();
    }

    public void speeds(){
        this.y -= 400 * Gdx.graphics.getDeltaTime();
    }

}
