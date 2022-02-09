package j01;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ReadingFile {

  public static void main(String[] args) {
    //
      Charset charset = StandardCharsets.UTF_8;
      Path path = Paths.get("datos.txt");
      final int ageLimit = 25;

      try {
          // Get lines from file as Stream of Strings.
          Stream<String> lines = Files.lines(path,charset);

          // Map lines to a List of Persona
          List<Persona> personList = lines.map(Persona::new).toList();
          // It's possible to do without this line and use directly:
          // lines.map(Persona::new).filter(...).map(...).forEach(...);

          personList.stream()
                  .filter(e -> e.isMinorOf(ageLimit))
                  .map(Persona::toString)
                  .forEach(System.out::println);
      }
      catch (IOException e) {
          System.out.println(e);
      }
  }

    static class Persona {

        private Optional<String> name, city;
        private Optional<Integer> age;

        public Persona(String line){
            String[] tokens = line.split(":");
            int nTokens = tokens.length;
            this.name = Optional.empty();
            this.city = Optional.empty();
            this.age = Optional.empty();

            if (nTokens>0 && !tokens[0].equals("")) this.name = Optional.of(tokens[0]);
            if (nTokens>1 && !tokens[1].equals("")) this.city = Optional.of(tokens[1]);
            if (nTokens>2 && !tokens[2].equals("")) this.age = Optional.of(Integer.parseInt(tokens[2]));
        }

        public String toString(){
            String res;
            res = "Nombre: " + name.orElse("Desconocido");
            res += ". Población: " + city.orElse("Desconocido");
            res += ". Edad: "+ age.map(Object::toString).orElse("Desconocido");
            return res;
        }

        public boolean isMinorOf(int limit){
            return (age.isPresent() && age.get() < limit);
        }
    }

}
