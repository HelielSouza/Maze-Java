public class Main {
    public static void main(String[] args) {
        
        try {

            System.out.println("\n\n ----------------------- LABIRINTO  -----------------------");
            System.out.println("Como se chama o file do maze?");

            String arq = Keyboard.getString(); //usa a classe keyboard para capturar o que foi digitado
            Maze l1 = new Maze(arq); // é criado um novo objeto maze e como parametro desse objeto vai o que foi digitado
            System.out.println("\n(Antes da solucao)");
            System.out.println(l1); //exibe esse maze

            l1.solveMaze(); //metodo de resolucao do maze
            System.out.println("\n(Resolvido) \n" + l1);
            
        } 
        catch (Exception e) 
        {
            System.out.println(e.getMessage()+"\n");
        }
    }
}