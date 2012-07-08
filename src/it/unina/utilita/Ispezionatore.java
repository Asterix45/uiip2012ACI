/**
 * Basato su ReflectionTest di Cay Horstmann
 * http://www.horstmann.com/
 */

package it.unina.utilita;

public class Ispezionatore {
    
    public static void esegui(String nomeClasse) {
        System.out.println("Sto ispezionando la classe: " + nomeClasse);
        System.out.println("---------------------------------------------");
        System.out.println();
        try {
            java.lang.Class classe = Class.forName(nomeClasse);
            System.out.print("class " + classe.getName());
            ispezionaSuperclasse(classe);
            System.out.println(" {");
            ispezionaCampi(classe);
            ispezionaCostruttori(classe);
            ispezionaMetodi(classe);
            System.out.println();
            System.out.println("}");
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void ispezionaSuperclasse(Class classe) {
        Class superClasse = classe.getSuperclass();
        if (superClasse != null) {
            System.out.print(" extends " + superClasse.getName());
        }
    }
    
    private static void ispezionaCampi(Class classe) {
        System.out.println();
        System.out.println("    //  Proprieta'");
        java.lang.reflect.Field[] campi = classe.getDeclaredFields();        
        for (int i = 0; i < campi.length; i++) {
            java.lang.reflect.Field campo = campi[i];
            Class type = campo.getType();
            String tipoCampo = type.getName();
            String nomeCampo = campo.getName();
            String modificatori = java.lang.reflect.Modifier.toString(campo.getModifiers());
            System.out.println("    " + modificatori + " " + tipoCampo + " " + nomeCampo + ";");
        }
    }    

    private static void ispezionaCostruttori(Class classe) {
        System.out.println();
        System.out.println("    //  Costruttori");
        java.lang.reflect.Constructor[] costruttori = classe.getDeclaredConstructors(); 
        for (int i = 0; i < costruttori.length; i++) {
            java.lang.reflect.Constructor costruttore = costruttori[i];
            String nomeCostruttore = costruttore.getName();
            String modificatori = java.lang.reflect.Modifier.toString(costruttore.getModifiers());
            System.out.print("    " + modificatori + " " + nomeCostruttore + "(");
            Class[] tipiParametri = costruttore.getParameterTypes();
            for (int j = 0; j < tipiParametri.length; j++) {
                if (j > 0) System.out.print(", ");
                System.out.print(tipiParametri[j].getName());
            }
            System.out.println(");");
        }
    }
    
    private static void ispezionaMetodi(Class classe) {
        System.out.println();
        System.out.println("    //  Metodi");
        java.lang.reflect.Method[] metodi = classe.getDeclaredMethods();        
        for (int i = 0; i < metodi.length; i++) {
            java.lang.reflect.Method metodo = metodi[i];
            Class tipoDiRitorno = metodo.getReturnType();
            String nomeTipoDiRitorno = tipoDiRitorno.getName();
            String nomeMetodo = metodo.getName();
            String modificatori = java.lang.reflect.Modifier.toString(metodo.getModifiers());
            System.out.print("    " + modificatori + " " + nomeTipoDiRitorno + " " + nomeMetodo + "(");
            Class[] tipiParametri = metodo.getParameterTypes();
            for (int j = 0; j < tipiParametri.length; j++) {
                if (j > 0) System.out.print(", ");
                System.out.print(tipiParametri[j].getName());
            }
            System.out.println(");");
        }
    }
    
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Utilizzo: it.unina.utilita.Ispezionatore <nomeClasse>");;
        } else {
            esegui(args[0]);
        }
    }

}
