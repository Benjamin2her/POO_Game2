package poo.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

// CLASE Carretera QUE HEREDA DE CLASE Object
public class Carretera extends Object{

    // CONSTRUCTOR

    public Carretera(int x, int y, Texture img){
        super(x, y, 840, 650, img);
    }

    // SOBRESCRIBIR METODO VIRTUAL DE CLASE Object, PARA SER USADO EN POLIMORFISMO
    @Override
    public void finalize(){
        super.finalize();
        System.out.println("Carretera destroyed");
    }

    public void acelera() {
        if(velocidad < velocidadLimite){
            this.velocidad += 0.075;
        }
        this.y -= 200 * velocidad * Gdx.graphics.getDeltaTime();
        if(this.y <= -600) this.y = 0;
    }
    public void frena(){
        if(velocidad > 0){
            this.velocidad -= 0.05;
        }else{
            velocidad = 0;
        }
        if(this.y < - 600) this.y = 0;
        this.y -= 200 * velocidad * Gdx.graphics.getDeltaTime();
    }

}
