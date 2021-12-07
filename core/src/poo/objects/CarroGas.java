package poo.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class CarroGas extends Carro{
    // CONSTRUCTORES SOBRECARGADOS
    public CarroGas(int x, int y, int w, int h, Texture img){
        super(x, y, w, h, img);
    }

    public CarroGas(int x, int y, Texture img){
        super(x, y, 64, 129, img);
    }


    protected void finalize(){
        super.finalize();
        System.out.println("RIP carrito");
    }

    // SOBRESCRIBIR METODO VIRTUAL DE CLASE Object, PARA SER USADO EN POLIMORFISMO
    @Override
    public void acelera(){
        this.y -= 100 * velocidadLimite * Gdx.graphics.getDeltaTime();
    } //
    public void moverIzquierda(){
        this.x -= 2000 * Gdx.graphics.getDeltaTime();
        if(this.x < 140) this.x = 140;
    }
    public void moverDerecha(){
        this.x += 2000 * Gdx.graphics.getDeltaTime();
        if(this.x > 650) this.x = 650;
    }
    public int chocar(Object a, Object b){
        if (velocidad > 0){
            gas += 90;
            score += 500;
        }

        //carro verdeVerde
        System.out.println("Choco verde");
        return 3;
    }

    public void addScore() {

        score-= 1000;
        if (score < 0) score = 0;
        System.out.println("-50 pts");
    }

}
