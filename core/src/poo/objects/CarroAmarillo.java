package poo.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class CarroAmarillo extends Carro{
    // CONSTRUCTOR
    public CarroAmarillo(int x, int y, Texture img){
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

    public int chocar(Object player, Object enemigo){
        int di = MathUtils.random(0,1);
        score -= 100;
        if(score < 0) score = 0;
        switch (di){
            case 0:
                player.moverDerecha();
                enemigo.moverIzquierda();
                System.out.println("Choco amarillo por la derecha");
                break;

            case 1:
                player.moverIzquierda();
                enemigo.moverDerecha();
                System.out.println("Choco amarillo por la izquierda");
                break;
        }

        return 1;
    }

    public void addScore() {
        score+=50;
        System.out.println("+50 pts");
    }
}
