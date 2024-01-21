import java.io.File;

public class MostrarDirectorioActual {

  public static void main(String[] args) throws Exception {

    File f = new File("./");

    for (File file : f.listFiles()) {
      System.out.println(file.getName());
    }

  }

}
