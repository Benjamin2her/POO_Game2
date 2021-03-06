package poo.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;


import poo.objects.Camion;
import poo.objects.Carretera;
import poo.objects.CarroAmarillo;
import poo.objects.CarroAzul;
import poo.objects.CarroGas;
import poo.objects.Object;
import poo.objects.Player;


public class PooGame extends ApplicationAdapter {
	private Texture camionImage;
	private Texture highwayImage;
	private Texture playerImage;
	private Texture carritoAzulImage;
	private Texture carritoAmarilloImage;
	private Texture carritoGasImage;



	private Sound claxonSound;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Player player;
	private Carretera carretera;
	private Array<Object> carritos; //carros
	private long lastCarritoTime;

	private BitmapFont font;

	@Override
	public void create () {

		// CARGADO DE IMAGENES

		camionImage = new Texture(Gdx.files.internal("trashmaster.png"));
		highwayImage = new Texture(Gdx.files.internal("background-1.png"));
		playerImage = new Texture(Gdx.files.internal("carrito_player.png"));
		carritoAzulImage = new Texture(Gdx.files.internal("carrito_azul.png"));
		carritoAmarilloImage = new Texture(Gdx.files.internal("carrito_amarillo.png"));
		carritoGasImage = new Texture(Gdx.files.internal("carrito_gas.png"));

		claxonSound= Gdx.audio.newSound(Gdx.files.internal("clasico.wav"));


		//	Inicializamos font
		font= new BitmapFont();

		// INICIALIZACIÓN DE CAMERA Y SPRITEBATCH

		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		batch = new SpriteBatch();

		// INSTANCIAMOS LA IMAGEN DEL JUGADOR EN EL JUEGO USANDO UN RECTÁNGULO
		player	  = new Player(800/2 - 64/2, 20, playerImage);
		carretera = new Carretera(0,0, highwayImage);
		carritos = new Array<Object>();

	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0.2f, 1);

		camera.update();

		// RENDERIZADO DE IMAGENES
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(carretera.image, carretera.x, carretera.y);
		batch.draw( player.image, player.x, player.y);

		//RENDERIZADO DE TEXTO -------------------------
		font.getData().setScale(2f);
		font.setColor(1,1,1,1);
		font.draw(batch, "CARRITOS", 900,600);

		font.draw(batch, "Puntos", 900, 500);
		font.draw(batch, player.getPuntos() + "", 900, 475);
		font.draw(batch, "Velocidad", 900, 425);
		font.draw(batch, player.getVelocidad() + "", 900, 400);
		font.draw(batch, "Gasolina", 900, 350);
		font.draw(batch, player.getGasolina() + "", 900, 325);


		for(Object carrito: carritos) {
			batch.draw(carrito.image, carrito.x, carrito.y);
		}

		batch.end();


		// DETECTA EVENTO DE TECLADO Y AFECTA POSICIÓN DEL PLAYER
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			player.giraIzquierda();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			player.giraDerecha();
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
			claxonSound.play(0.3f, 1f, 1f);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			carretera.acelera();
		}
		else{
			carretera.frena();
		}


		if(!player.sinGasolina()) {

			// CADA SEGUNDO LLAMA MÉTODO DE UTILIDAD PARA GNERAR NUEVOS CARROS
			if (System.currentTimeMillis() - lastCarritoTime > 500) {

				spawnCarrito();
				player.actualizaGasolina();
				System.out.println("Elementos: " + carritos.size);

			}


			// RECORRE ARREGLO DE CARROS, DETERMINA SU AVANCE MEDIANTE POLIMORFISMO,
			// SI LLEGA AL FINAL DE PANTALLA Y SU COLISIÓN CON PLAYER
			for (Array.ArrayIterator<Object> iter = carritos.iterator(); iter.hasNext(); ) {
				Object carrito = iter.next();

				// MÉTODO PARA COMPORTAMIENTO POLIMÓRFICO
				carrito.acelera();


				// DETECTA SI El carro se sale del mapa Y SUMA PUNTOS DEPENDIENDO DE QUE CARRO ES
				if (carrito.y + 64 < -100) {
					if (player.getVelocidad() > 0) {
						carrito.addScore();
					}
					iter.remove();
				}


				// DETECTA COLISIÓN CON PLAYER
				if (carrito.overlaps(player)) {
					if(carrito.chocar(player, carrito) == 3) iter.remove();
				}
			}
		}
		// TERMINA EL JUEGO AL QUEDARSE SIN GASOLINA
		if(player.sinGasolina()) {
			int i = 25;
			int x;
			do{
				if(i % 2 == 0) x = 1;
				else x = 0;

				batch.begin();
				font.getData().setScale(5f);
				font.setColor(1,x,x,1);
				font.draw(batch, "GAME OVER", 200 + i, 650/2 + i);
				batch.end();
				i--;
			}while(i > 0);


			if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) && !Gdx.input.isKeyPressed(Input.Keys.UP)) {
				player = null;
				for (Array.ArrayIterator<Object> iter = carritos.iterator(); iter.hasNext(); ) {
					iter.next();
					iter.remove();
				}

				System.runFinalization();
				System.gc();
				Gdx.app.exit();
			}


		}


	}
	
	@Override
	public void dispose () {
		playerImage.dispose();
		camionImage.dispose();
		carritoAmarilloImage.dispose();
		carritoGasImage.dispose();
		carritoAzulImage.dispose();
		highwayImage.dispose();
		batch.dispose();
		claxonSound.dispose();
	}

	// MÉTODO DE UTILIDAD PARA GENERAR LoS carros EN EL ESCENARIO
	private void spawnCarrito() {
		Object trafico;

		int carril=MathUtils.random(0,3);
		int limIn, limSup;

		switch(carril){
			case 0:
				limIn=175;
				limSup=219;
				break;
			case 1:
				limIn=290;
				limSup=353;
				break;
			case 2:
				limIn=420;
				limSup=485;
				break;
			default:
				limIn=550;
				limSup=610;
				break;
		}

		int tipoCarro=MathUtils.random(0, 3);//aleatorio para ver en que carril sakldránswitch (tipoCarro){

		switch(tipoCarro){
			case 0:
				trafico = new Camion(MathUtils.random(limIn, limSup), 650, camionImage);
				break;
			case 1:
				trafico = new CarroAmarillo(MathUtils.random(limIn, limSup) ,650,carritoAmarilloImage);
				break;
			case 2:
				trafico = new CarroAzul(MathUtils.random(limIn, limSup),650,carritoAzulImage);
				break;
			default:
				trafico = new CarroGas(MathUtils.random(limIn, limSup),650,carritoGasImage);
				break;
		}
		carritos.add(trafico);
		lastCarritoTime = System.currentTimeMillis();
	}

}

