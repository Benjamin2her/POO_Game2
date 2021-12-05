package poo.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import poo.objects.Object;
import poo.objects.Player;
import poo.objects.Drop;
import poo.objects.Hailstone;

public class PooGame extends ApplicationAdapter {


	private Texture dropWaterImage;
	private Texture dropOilImage;
	private Texture bucketImage;
	private Texture hailstoneImage;
	private Texture camionImage;
	private Texture highwayImage;
	private Texture playerImage;
	private Texture carritoAzulImage;
	private Texture carritoAmarilloImage;
	private Texture carritoGasImage;


	//private
	private Sound 	dropSound;
	private Sound hitSound;
	private Music 	rainMusic;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Player player;
	private Array<Object> rainDrops; //carros
	private long lastDropTime;
	private int score = 0;
	private int lives = 100;
	private float angle = 0f;
	// puntos, velocidad, gasolina
	private int puntos = 0;
	private int velocidad = 0;
	private int gasolina = 100;
	private BitmapFont font;
	
	@Override
	public void create () {

		// CARGADO DE IMAGENES
		dropWaterImage = new Texture(Gdx.files.internal("drop_water.png"));
		dropOilImage = new Texture(Gdx.files.internal("drop_oil.png"));
		hailstoneImage = new Texture(Gdx.files.internal("hailstone.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));
		camionImage = new Texture(Gdx.files.internal("trashmaster.png"));
		highwayImage = new Texture(Gdx.files.internal("background-1.png"));
		playerImage = new Texture(Gdx.files.internal("carrito_player.png"));
		carritoAzulImage = new Texture(Gdx.files.internal("carrito_azul.png"));
		carritoAmarilloImage = new Texture(Gdx.files.internal("carrito_amarillo.png"));
		carritoGasImage = new Texture(Gdx.files.internal("carrito_gas.png"));
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
		//camera.setToOrtho(false, 860, 480);
		camera.setToOrtho(false);

		batch = new SpriteBatch();

		// INSTANCIAMOS LA IMAGEN DE LA CUBETA EN EL JUEGO USANDO UN RECTÁNGULO
		player	  = new Player(800/2 - 64/2, 20, playerImage);

		rainDrops = new Array<Object>();

	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0.2f, 1);

		///camera.rotate(angle+= 0.1f);
		camera.update();

		// RENDERIZADO DE IMAGENES
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(highwayImage, 0, 0);
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

		for(Object raindrop: rainDrops) {
			batch.draw(raindrop.image, raindrop.x, raindrop.y);
		}
		batch.end();

		// DETECTA EVENTO DEL MOUSE Y AFECTA POSICIÓN DEL PLAYER
		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			player.x = touchPos.x - 64 / 2;
		}

		// DETECTA EVENTO DE TECLADO Y AFECTA POSICIÓN DEL PLAYER
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) player.x -= 500 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) player.x += 500 * Gdx.graphics.getDeltaTime();

		// EVITA QUE LA IMAGEN DEL PLAYER SALGA DEL ÁREA DE JUEGO
		if(player.x < 140) player.x = 140;
		if(player.x > 650) player.x = 650;

		// CADA SEGUNDO LLAMA MÉTODO DE UTILIDAD PARA GNERAR NUEVAS GOTAS
		if(System.currentTimeMillis() - lastDropTime > 1000) {
			spawnRaindrop();
			System.out.println("Elementos: " + rainDrops.size);
			System.runFinalization();
			System.gc();
		}

		// RECORRE ARREGLO DE GOTAS, DETERMINA SU AVANCE MEDIANTE POLIMORFISMO, SI LLEGA AL FINAL DE PANTALLA Y SU COLISIÓN CON PLAYER
		for (Array.ArrayIterator<Object> iter = rainDrops.iterator(); iter.hasNext(); ) {
			Object raindrop = iter.next();
			// MÉTODO PARA COMPORTAMIENTO POLIMÓRFICO
			raindrop.speed();
			// DETECTA SI LA GOTA LLEGA AL SUELO
			if(raindrop.y + 64 < 0) {
				//hitSound.play();
				iter.remove();
				lives--;
				System.out.println("Vidas: " + lives);
			}
			// DETECTA COLISIÓN CON PLAYER
			if(raindrop.overlaps(player)) {
				//dropSound.play();
				iter.remove();
				score++;
				System.out.println("Score: " + score);
			}
		}

		// TERMINA EL JUEGO EN CASO DE PERDER TODAS LAS VIDAS Y ELIMINA PLAYER Y OBJETOS RESTANTES
		if(lives == 0) {
			player = null;
			for (Array.ArrayIterator<Object> iter = rainDrops.iterator(); iter.hasNext(); ) {
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
		dropWaterImage.dispose();
		dropOilImage.dispose();
		hailstoneImage.dispose();
		bucketImage.dispose();
		//dropSound.dispose();
		//hitSound.dispose();
		//rainMusic.dispose();
		batch.dispose();
	}

	// MÉTODO DE UTILIDAD PARA GENERAR LAS GOTAS EN EL ESCENARIO
	private void spawnRaindrop() {
		Object rain;

	int random=MathUtils.random(0, 3);

	switch(random){
			case 0:
				rain = new Hailstone(MathUtils.random(140, 650),650, camionImage);
				break;

			case 1:
				rain = new Drop(MathUtils.random(140, 650),650,carritoAmarilloImage);
				break;

			case 2:
				rain = new Drop(MathUtils.random(140, 650),650,carritoAzulImage);
				break;

			default:
				rain = new Drop(MathUtils.random(140, 650),650,carritoGasImage);
				break;
			}
		rainDrops.add(rain);
		lastDropTime = System.currentTimeMillis();
		}
	}

