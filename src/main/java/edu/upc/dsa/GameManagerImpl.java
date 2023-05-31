package edu.upc.dsa;

import edu.upc.dsa.models.Objeto;
import edu.upc.dsa.models.Usuario;
import edu.upc.dsa.models.dto.UsuarioTO;
import edu.upc.eetac.dsa.FactorySession;
import edu.upc.eetac.dsa.Session;
import edu.upc.eetac.dsa.model.User;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class GameManagerImpl implements GameManager {

    HashMap<String, Usuario> Usuarios;
    List<Usuario> listaUsuarios;
    HashMap<String, Objeto> Objetos;
    protected List<Objeto> listaObjetos;
    private static GameManager instance;
    final static Logger logger = Logger.getLogger(GameManagerImpl.class);

    public GameManagerImpl(){
        this.listaUsuarios = new ArrayList<>();
        this.Usuarios = new HashMap<>();
        this.listaObjetos = new ArrayList<>();
        this.Objetos = new HashMap<>();
    }

    public static GameManager getInstance() {
        if (instance==null) instance = new GameManagerImpl();
        return instance;
    }

    @Override
    public void registrarUsuario(String nombre, String correo, String password) {

        if (Usuarios.get(correo) == null){

            this.listaUsuarios.add(new Usuario(nombre, correo, password));

            logger.info("Se ha realizado correctamente");
        }
        else
            logger.info("El correo ya existe con un usuario");
    }

    @Override
    public int addUsuario(String nombre, String correo, String password) {
        Session session = null;
        int userID = 0;
        try {
            session = FactorySession.openSession();
            UsuarioTO u = new UsuarioTO(nombre, correo, password);
            session.save(u);
        }
        catch (Exception e) {
            // LOG
        }
        finally {
            session.close();
        }

        return userID;

    }

    @Override
    public void addObjeto(String nombre, String descripcion, double precio, String fotoImagen) {
        this.listaObjetos.add(new Objeto(nombre, descripcion, precio, fotoImagen));
        logger.info("Se ha añadido correctamente");
    }

    @Override
    public void login(String correo, String password) {

        Usuario usuario = null;
        for (Usuario u : listaUsuarios) {
            if (u.getCorreo().equals(correo)) {
                usuario = u;
                break;
            }
        }
        if (usuario != null && usuario.getPassword().equals(password)) {
            logger.info("Login con éxito");
        } else {
            logger.info("Contraseña incorrecta");
        }
    }

    @Override
    public boolean loginORM(String correo, String password) {
        Session session = null;
        UsuarioTO usuario = null;
        try {
            session = FactorySession.openSession();
            usuario= getUserByEmail(correo);
            if (usuario.getCorreo().equals(correo)&(usuario.getPassword().equals(password))){
                logger.info("usuario loggedo");
                return true;

            }
            logger.info("usuario NO loggedo");
            return false;
            /*User u = new User(email, password);
            session.save(u);*/
        }
        catch (Exception e) {
            // LOG
        }
        finally {
            session.close();
        }

        return false;
        }


    @Override
    public Objeto hacerCompra(String Usuario, String nombreObjeto) {

        Usuario usuario = getUsuarioPorNombre(Usuario);
        if (usuario == null) {
            logger.info("Usuario " + Usuario + " no existe");
        }
        else {
            Objeto objeto = getObjetoPorNombre(nombreObjeto);
            if (usuario.getDsaCoins() < objeto.getPrecio()) {
                logger.info("No tienes money");
            }
            else{
                usuario.getListaObjetosComprados().add(objeto);
                double saldo = usuario.getDsaCoins() - objeto.getPrecio();
                usuario.setDsaCoins(saldo);
                logger.info("Objeto " + nombreObjeto + " comprado");
                logger.info(Usuario + " ahora tienes: " + saldo + " dsaCoins");
                return objeto;
            }
        }
        return null;
    }

    @Override
    public List<Objeto> listadeObjetos() {
        logger.info("Lista de objetos: " + listaObjetos.toString());
        return this.listaObjetos;
    }

/*    @Override
    public List<Objeto> listadeObjetosOrdenadosPorPrecio() {
        this.listaObjetos.sort(new Comparator<Objeto>() {
            public int compare(Objeto o1, Objeto o2) {
                return Double.compare(o2.getPrecio(), o1.getPrecio());
            }
        });
        logger.info("Lista ordenada por precio ascendente: " + listaObjetos.toString());
        return listaObjetos;

    } */


    //extras

    public Usuario getUsuarioPorNombre(String nombre){
        for (Usuario u: this.listaUsuarios) {
            if(u.getNombre().equals(nombre)){
                return u;
            }
        }
        return null;
    }

    public Objeto getObjetoPorNombre(String nombre){
        for (Objeto o: this.listaObjetos) {
            if(o.getNombre().equals(nombre)){
                return o;
            }
        }
        return null;
    }

    public Usuario getUsuarioPorCorreo(String correo){
        for (Usuario u: this.listaUsuarios) {
            if(u.getCorreo().equals(correo)){
                return u;
            }
        }
        return null;
    }

    @Override
    public UsuarioTO getUserByEmail(String correo) {
        Session session = null;
        UsuarioTO usuario = null;
        try {
            session = FactorySession.openSession();
            usuario = (UsuarioTO) session.get(UsuarioTO.class, "correo", correo);
        }
        catch (Exception e) {
            // LOG
        }
        finally {
            session.close();
        }

        return usuario;
    }

    @Override
    public void clear() {
        this.listaObjetos.clear();
    }

    @Override
    public int size() {
        return this.listaObjetos.size();
    }

    // public int size() {
    //   int ret = this.tracks.size();
    // logger.info("size " + ret);

    //return ret;
    //}

}