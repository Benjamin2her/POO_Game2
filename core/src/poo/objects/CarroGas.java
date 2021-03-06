package poo.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;


public class CarroGas extends Carro{
    // CONSTRUCTOR
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

            gas += 20;
            score += 500;

        System.out.println("Choco verde");
        return 3;
    }

    public void addScore() {

        score-= 1000;
        if (score < 0) score = 0;
        System.out.println("-50 pts");
    }

}
