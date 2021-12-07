package poo.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import poo.objects.Camion;
import poo.objects.Carretera;
import poo.objects.Carro;
import poo.objects.CarroAmarillo;
import poo.objects.CarroAzul;
import poo.objects.CarroGas;
import poo.objects.Object;
import poo.objects.Player;
import poo.objects.Interfaz;
public class PooGame extends ApplicationAdapter {


	private Texture camionImage;
	private Texture highwayImage;
	private Texture playerImage;
	private Texture carritoAzulImage;
	private Texture carritoAmarilloImage;
	private Texture carritoGasImage;



	//private
//	private Sound 	dropSound;
//	private Sound hitSound;
//	private Music 	rainMusic;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Player player;
	private Carretera carretera;
	private Array<Object> carritos; //carros
	private long lastCarritoTime;
	private int score = 0;
	private int lives = 100;
	private float angle = 0f;
	// puntos, velocidad, gasolina
	private int puntos = 0;
	private int velocidad = 0;
	private int gasolina = 100;
	private BitmapFont font;
	private Interfaz interfaz;
	@Override
	public void create () {

		// CARGADO DE IMAGENES

		camionImage = new Texture(Gdx.files.internal("trashmaster.png"));
		highwayImage = new Texture(Gdx.files.internal("background-1.png"));
		playerImage = new Texture(Gdx.files.internal("carrito_player.png"));
		carritoAzulImage = new Texture(Gdx.files.internal("carrito_azul.png"));
		carritoAmarilloImage = new Texture(Gdx.files.internal("carrito_amarillo.png"));
		carritoGasImage = new Texture(Gdx.files.internal("carrito_gas.png"));
		interfaz = new Interfaz();

		// CARGA EFECTOS DE SONIDOS Y MÚSICA DE FONDO
		//dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		//hitSound = Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));
		//rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		// INICIA SONIDO DE FONDO
		//rainMusic.setLooping(true);
		//rainMusic.setVolume(0.2f);
		//rainMusic.play();

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

		///camera.rotate(angle+= 0.1f);
		camera.update();

		// RENDERIZADO DE IMAGENES
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(carretera.image, carretera.x, carretera.y);
		batch.draw( player.image, player.x, player.y);

		//RENDERIZADO DE TEXTO -------------------------
		font.draw(batch, "CARRITOS", 900,600);
		font.getData().setScale(2f);
		font.draw(batch, "Puntos", 900, 500);
		font.draw(batch, (puntos)+"", 900, 475);
		font.draw(batch, "Velocidad", 900, 425);
		font.draw(batch, velocidad+"", 900, 400);
		font.draw(batch, "Gasolina", 900, 350);
		font.draw(batch, (gasolina)+"", 900, 325);
//		interfaz.actualizarInterfaz();

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
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			carretera.acelera();
		}
		else{
			carretera.frena();
		}

		// EVITA QUE LA IMAGEN DEL PLAYER SALGA DEL ÁREA DE JUEGO


		// CADA SEGUNDO LLAMA MÉTODO DE UTILIDAD PARA GNERAR NUEVAS GOTAS
		if(System.currentTimeMillis() - lastCarritoTime > 500) {
			spawnCarrito();
			System.out.println("Elementos: " + carritos.size);
			System.runFinalization();
			System.gc();
		}

		// RECORRE ARREGLO DE CARROS, DETERMINA SU AVANCE MEDIANTE POLIMORFISMO, SI LLEGA AL FINAL DE PANTALLA Y SU COLISIÓN CON PLAYER
		for (Array.ArrayIterator<Object> iter = carritos.iterator(); iter.hasNext(); ) {
			Object carrito = iter.next();
			// MÉTODO PARA COMPORTAMIENTO POLIMÓRFICO
			carrito.acelera();


			// DETECTA SI El carro se sale del mapa Y SUMA PUNTOS DEPENDIENDO DE QUE CARRO ES
			if(carrito.y + 64 < -100) {
				//hitSound.play();
				iter.remove();

			}


			// DETECTA COLISIÓN CON PLAYER
			if(carrito.overlaps(player)) {
				int a = carrito.chocar(player, carrito);
				//dropSound.play()
				// iter.chocar();

				switch (a){
					case 1: //amarillo

						break;
					case 2: //azul

						break;
					case 3: // verde

						break;
					case 4: // camion

						break;
					default:
						break;
						//perdio();
				}
				score++;
				//System.out.println("Score: " + score);
			}
		}

		// TERMINA EL JUEGO EN CASO DE PERDER TODAS LAS VIDAS Y ELIMINA PLAYER Y OBJETOS RESTANTES
		if(lives == 0) {
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
	
	@Override
	public void dispose () {
		playerImage.dispose();
		camionImage.dispose();
		carritoAmarilloImage.dispose();
		carritoGasImage.dispose();
		carritoAzulImage.dispose();
		highwayImage.dispose();
		batch.dispose();
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

