package poo.objects;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

// CLASE Carro QUE HEREDA DE CLASE Object
public class Carro extends Object {

    // CONSTRUCTORES SOBRECARGADOS
    public Carro(int x, int y, int w, int h, Texture img){
        super(x, y, w, h, img);
    }
    public Carro(int x, int y, Texture img){
        super(x, y, 64, 129, img);
    }


    protected void finalize(){
        super.finalize();
        System.out.println("RIP carrito");
    }

    // SOBRESCRIBIR METODO VIRTUAL DE CLASE Object, PARA SER USADO EN POLIMORFISMO
    @Override
    public void acelera(){
        this.y -= 500 * Gdx.graphics.getDeltaTime();
    } //
    public void moverIzquierda(){
        this.x -= 2000 * Gdx.graphics.getDeltaTime();
        if(this.x < 140) this.x = 140;
    }
    public void moverDerecha(){
        this.x += 2000 * Gdx.graphics.getDeltaTime();
        if(this.x > 650) this.x = 650;
    }
    public void chocar(Object a){
        if(MathUtils.random(0,1) == 0){
            a.moverIzquierda();
            this.moverDerecha();
        }
        else{
            this.moverIzquierda();
            a.moverDerecha();
        }
    }


}