public class Maze implements Cloneable{
    
    private int                      numberOfRows;
    private int                      numberOfColumns;
    private char                     maze[][]; //refere se ao maze que será construido
    private Stack<Coordinate>        way; //refere se ao way que ele vai percorrer nesse maze
    private Stack<Coordinate>        inverse; //referese ao way que sera printado quando solveMaze o maze
    private Coordinate               current = null; //referese ao way current
    private Queue<Coordinate>         adjPos; //posicoes adjantes e posicao current
    private Stack<Queue<Coordinate>>  possibilities; 
    private char                     above;
    private char                     below;
    private char                     right;
    private char                     left; //esses metodos serao muito usados

    public Maze (String file) throws Exception{
        
        File maze_file = new File(file); //ta sendo instaciado do file para ca para ser usado
        File copy   = new File(file);

        int numberRow = maze_file.getAnInt(); //get int pega o numero da primeira row do file, convertendo o 5 que esta em string para int e guardando em quantidade de row
        String str = maze_file.getAString(); //ela vai na segunda column, faz um getString, a segunda row da matriz
        int numberColumn = str.length(); //ela conta quantos elementos tem na row

        if (!isValidMaze(maze_file, numberRow, numberColumn)){ //se ele for nao valido, dispara a exceção
            throw new Exception("Maze inválido");
        }

        this.numberOfRows = numberRow;
        this.numberOfColumns = numberColumn;

        GenerateArray (copy);

        way = new Stack<Coordinate>(this.numberOfColumns * this.numberOfRows);
    }

    private boolean hasExit() throws Exception{
        
        for(int i = 0; i < this.numberOfRows; i ++){ // acessa a quantidade de linhas na matriz gerada pelo maze
            for(int j = 0; j < this.numberOfColumns; j++){ //aqui acessa usando o this a qtd de colunas da matriz do labiorinto
                if (this.maze[i][j] == 'S'){ //se o maze tem o caracter s guardada em alguma dessas posicoes
                    return true; //retornara verdedeiro
                }
            }
        }
        return false; //se retornar falso é pq nao há saida
    }

    public boolean hasEntry() throws Exception{
    
        for (int i = 0; i < this.numberOfRows; i++) {
            for (int j = 0; j < this.numberOfColumns; j++) {
                if(maze[i][j] == 'E'){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean oneExit() throws Exception{

        int flag = 0;

        for(int i = 0; i<this.numberOfRows; i++){ //vai acessar usando o this a quantidade de linhas nesse maze
            for(int j = 0; j < this.numberOfColumns; j++){ //aqui a qdt de colunas nesse maze
                if (this.maze[i][j] == 'S'){ //aqui verifica se a row e a column corresponde igual ao caracter s
                    flag ++; //se toda vez que encontrar o element s, vai adcionar um na variavel de controle
                }
            }
        }
        if (flag == 1){ //se houver uma saida, blz
            return true;
        }
        return false; // caso contrario, ha mais de uma saida
    }

    private boolean oneEntry() throws Exception{ //esse metodo funciona da mesma forma do S
        
        int flag = 0; 

        for(int i = 0; i < numberOfRows; i++){
            for(int j = 0; j < numberOfColumns; j++){
                if (this.maze[i][j] == 'E'){
                    flag ++;
                }
            }
        }
        if (flag == 1){
            return true;
        }
        return false;
    }

    public boolean differentCharacter() throws Exception {
        
        for (int i = 0; i < this.numberOfRows; i++) {
            for (int j = 0; j < this.numberOfColumns; j++) {
                if (maze[i][j] != 'E' && maze[i][j] != 'S' && maze[i][j] != '#' && maze[i][j] != ' '){
                    return false;
                }
            }
        }
        return true;
    }

    

    public void solveMaze() throws Exception{ //o usuario chamara esse metodo na main
        if(!hasEntry()) {
            throw new Exception("Maze nao tem entrada");
         }
        if (!hasExit()){ //caso nao tenha saida, que esse metodo acha uma saida
            throw new Exception("Nao tem saida"); //lançará essa exceção
        }
        this.current = new Coordinate(searchEntry()); //logo a gente instancia o this current, que é um objeto da classe Coordinate, e a Coordinate da posicao current vai receber a Coordinate que passou no metodo searchEntry
        if (this.current == null){ //se for nulo, nao possui entrada
            throw new Exception("Não possui entrada");
        }
        
        //if (this.current == null){ //se for nulo, nao possui entrada
        //    throw new Exception("Não possui entrada");
        //}

        if (!oneEntry()){ //se for diferente desse metodo, lança a exceção
            throw new Exception("Possui mais de uma entrada");
        }

        if (!oneExit()){ //se for diferente desse metodo, lança a exceção
            throw new Exception("Possui mais de uma saida");
        }
        
        if(!differentCharacter()){ //se tiver caracteres estranhos
            throw new Exception("Maze tem carter diferente de: 'E' -- 'S' -- ' ' ");
        }
        //se estiver tudo correto nas questoes de saida e entrada, segue abelow
        //instanciará uma pilha de Coordinate chamada way
        this.way = new Stack<Coordinate>(getNumberOfColumns() * getNumberOfRows()); //essa oilha de coordenadas, será as coordenadas que levam a saida do maze desde sua entrada
        //logo é instanciado uma pilha de pilha de Coordinate chamada possibilities
        //caso a pos current esteja em uma bifurcação, terá mais de uma Coordinate a dispor para reter nessa pilha de possibildiades
        this.possibilities = new Stack<Queue<Coordinate>> (getNumberOfColumns() * getNumberOfRows()); //essa pilha terá a quantidade de colunas vezes a quantidade de linahs 


        //aqi tem o algoritmo principal desse metodo
        while(getWhereIAm() != 'S'){ //esse while ficaria rodando no looping até que ele encontra-se a saida, ele pega o getEstou e verifica a igualdade com o 'S'
            populateAdj(); //senão ele vai preeencher essa pilha de adjacentes
        //caso a pilha nao esteja vazia, ele nao entrará nesse while abelow e irá direto pra row 135
            while(this.adjPos.isEmpty()){
                //se a pilha for vazia, ele entrará no modo regressivo, quando nao for mais, sai desse while, ou seja tem way a percorrer
                this.current = way.getAItem(); //aqui recuperara um item da pilha de way, guardando na current
                this.way.removeAItem(); //aqui a pilha de way retirará um item do topo
                this.maze[this.current.getRow()][this.current.getColumn()] = ' '; //aqui nessa localidade será posto o caracter retirado, mostrando que o way está se desfazendo 
                this.adjPos = this.possibilities.getAItem(); // a pilha de adj vai recolher um item da pilha de possibildiadess
                this.possibilities.removeAItem(); //e a pilha de possibilities removerá esse item. 
            }
            this.current = this.adjPos.getAItem(); //aqui recuperará um item da pilha de adjacentes, no caso o valor do topo e atribuirá na posicao current,
            if (getWhereIAm() != 'S'){ //se onde eu estou for diferente de S, no caso a posicao current
                this.adjPos.removeAItem(); //removerá um item da pilha de adjacentes um item
                this.maze[this.current.getRow()][this.current.getColumn()] = '*'; //aqui será colocado um caracter, pra indicar que o way foi percorrido
                this.way.SaveAnItem(this.current); //se for percorrido, a pilha de way guardará um item, que é a posicao current
                this.possibilities.SaveAnItem(this.adjPos); //e aqui a pilha de possibildades guardará um item, que é a pilha de adjacentes, depois dela ter sido retirada a posicao current              
            }
        }
        //o while ficará rodando, mas caso ele nao tem para onde ir, ele ficara no modo regressivo
     
        travelInverse(); //encontrando a saindo ele ira percorrer o inverse, pq percorrer inverse? pq se fosse printar o way percorrido, ele tirará a ultima Coordinate percorrida, ficando as coordenadas ao contario
        printSolution();
    }

    private void travelInverse() throws Exception{

        inverse = new Stack<Coordinate>(way.getQuantidade()); //aqui estamos instanciando de elementos no way

        while(!way.isEmpty()){ //enquanto o way for diferente de vazio
            inverse.SaveAnItem(way.getAItem()); //ela vai desempilhar e jogar na pilha de inverse
            way.removeAItem(); //printara a Coordinate correta
        }
       // System.out.println("\n");
    }
    
    public void printSolution() throws Exception{
        
        System.out.println("----------------------- COORDENADAS -----------------------");
        System.out.println("\nEssas sao as coordenadas que levam ao final do maze: ");

        while(!inverse.isEmpty()){
            System.out.println(" " + inverse.getAItem()); //vai na pilha de inverse e percorre element a eleemento
            inverse.removeAItem(); // ao mesmo tempo vai removendo
        }
        System.out.println("\n------------------------ RESOLUCAO ------------------------");
    }

    private void populateAdj() throws Exception{ //é um metodo privado para ser usado somente no maze
    //uma visao geral desse metodo é que ele valida primeiro se qualquer direção é vazia, nula. Caso ele tente contar posições que estejam fora dos limites do maze
        
        this.adjPos = new Queue<Coordinate>(3);
        Coordinate coorAdj; //declarado um objeto do tipo Coordinate

        coorAdj = aboveCoordinate();
        if (coorAdj != null ){ //aqui ele valida se a Coordinate é diferente de nula. senao tivesse esse if, poderia acusar erro de array
            if (getAbove() == ' ' || getAbove() == 'S') //se enabove fosse vazio ou o caracter S, seria valida..
                this.adjPos.SaveAnItem(coorAdj);  //entao a adjecentes recolheria essa Coordinate
        }

        coorAdj = belowCoordinate();
        if (coorAdj != null && coorAdj.getRow() < getNumberOfRows()){ //aqui valida se ta dentro dos limites do maze
            if (getBelow() == ' ' || getBelow() == 'S') //se a Coordinate debelow estivesse vazio ou com a saida, seria valida..
                this.adjPos.SaveAnItem(coorAdj); //recolheria essa Coordinate e colocaria na pilha de adjacentes
        }

        coorAdj = rightCoordinate();
        if (coorAdj != null && coorAdj.getColumn() < getNumberOfColumns()){ //aqui valida se a posicao right esta dentro dos limites do maze
            if (getRight() == ' ' || getRight() == 'S') //verifica se a Coordinate da right é vazia ou saida
                this.adjPos.SaveAnItem(coorAdj);//para guarda um item na pilha de adjacentes
        }

        coorAdj = leftCoordinate();
        if (coorAdj != null){ //valida se esta nos limites
            if (getLeft() == ' ' || getLeft() == 'S') // se há um espaço vazio ou a saida
                this.adjPos.SaveAnItem(coorAdj); //colocando um item na pilha de adjacentes
        }
    }

    //o raciocinio é o mesmo para todas as direções
    //de above
    private Coordinate aboveCoordinate() throws Exception {
        Coordinate ret = null; //declaramos um variavel nula chamada ret
        try {
            ret = new Coordinate(this.current.getRow() - 1, this.current.getColumn()); //aqui ele tenta instanciar uma Coordinate naquela posicao, se nao der erro
        } catch (Exception e) {
            return null; //se der erro, por exemplo, a coodenada ser negativo, retorna nulo
        }
        return ret; //senao retorna ret
    }

    //debelow
    private Coordinate belowCoordinate() throws Exception {
        Coordinate ret = null;
        try {
            ret = new Coordinate(this.current.getRow() + 1, this.current.getColumn());
        } catch (Exception e) {
            return ret;
        }
        return ret;
    }

    //da right
    private Coordinate rightCoordinate() throws Exception {
        Coordinate ret = null;
        try {
            ret = new Coordinate(this.current.getRow(), this.current.getColumn() + 1);
        } catch (Exception e) {
            return ret;
        }
        return ret;
    }

    //da left
    private Coordinate leftCoordinate() throws Exception {
        Coordinate ret = null;
        try {
            ret = new Coordinate(this.current.getRow(), this.current.getColumn() - 1);
        } catch (Exception e) {
            return ret;
        }
        return ret;
    }

    private Coordinate searchEntry() throws Exception{ //esse metodo retorna uma entrada que será a entrada do maze, ou seja, onde no maze estará o caracter E

        Coordinate ret = null; //declara um retorno, que será esse ret que é a Coordinate current, onde estará o E, sendo nulo
        int cont = 0;

        for(int i=0; i<this.numberOfRows; i++){ //aqui percorre todas as linhas do maze
            for(int j=0; j<this.numberOfColumns; j++){ //aqui percorre todas as colunas do maze

                // aqui ele valida se o E está na borda do maze
                if ((i == 0 || i == this.numberOfRows - 1 )) { //se o i for 0, ou seja, a primeira posicao da matriz, isso significa, a primeira row dessa matriz, ele estará na parede de above
                                            //ou se estiver tambem na ultima row do maze, que é qtd de linhas(total) - 1, ou seja, a ultima
                    if (this.maze[i][j] == 'E'){ // e se a posicao dentro dessa matriz for igual a E...
                        ret = new Coordinate(i, j); //ele declara essa nova Coordinate usando o i e o j.
                        cont++; //aqui contará um
                    }
                    if(this.maze[i][j] == ' ') //se tiver uma posicao livre, ele valida se tem ou nao parede...
                        throw new Exception("Sem parede nas bordas"); //lançando essa exceção
                }
                if ((j == 0 || j == this.numberOfColumns - 1 )){ //se a column j for 0, a primeira column dessa matriz, parede da left
                             //ou se esse j for a numberOfColumns(total)-1, significa que é a ultima column dessa matriz, parede da right
                    if (this.maze[i][j] == 'E'){ //sendo essas paredes sendo igual a E
                        ret = new Coordinate(i, j); // entao é valida e é atribuito ao ret, retorna, vai receber uma nova Coordinate na posicao i e j
                        cont++; //aqui contará um
                    }
                    if(this.maze[i][j] == ' ') //se o caracter da borda for uma espaço vazio, tem um buraco nesse maze 
                        throw new Exception("Sem parede nas bordas"); //lançando essa exceção 
                }
            }
        }
        if (cont>1) //se o contador contar mais que um, é pq tem mais de uma entrada
            throw new Exception("Mais de uma entrada");
        return ret; // caso tudo esteja certo, retorna a nova isntanciação
    }

  

    private boolean isValidMaze(File maze, int numberofR, int numberofC) throws Exception{ //ele instacia o file, quantidade de linhas e colunas  

        String str; 
        int column;
 
        for(int i = 2; i<=numberofR; i++ ){ //ela pega da segunda row e percorre 
            str = maze.getAString(); //o obejto lab chama o metodo getString para poder ler a string usando a str
            column = str.length(); //ela recebe o size que esta percorrendo na column e guarda nessa variavel column
            if (numberofC != column){ //se a variavel local qtdColuna for diferente dessa variavel column, retornará falso
                return false; 
            }
        }
        str = maze.getAString(); // Tenta um outro get
        if (str!= null){ //tem mais row que o normal, retorna falso
            return false;
        }
        return true; //caso passe por todas as validacoes, o maze é valido
    }

    private void GenerateArray(File copy){
        try {
            
            String str = null; 
            copy.getAnInt(); //vai na copy, vai ver o valor inteiro na primeira row
            
            this.maze = new char[this.numberOfRows][this.numberOfColumns]; //esse atributo maze receberá esses cavalores que ja passaram por validação

            for(int i = 0; i < this.numberOfRows; i ++){ 
                str = copy.getAString(); //ele sempre vai pegar e copiar uma string na row

                for(int j = 0; j < this.numberOfColumns; j ++){
                    this.maze[i][j] = str.charAt(j); //aqui vai receber cada posição do maze
                    Coordinate cor = new Coordinate(i, j);
                }
            }           
        } 
        catch (Exception e) {
              System.out.println(e);//nao vai dar erro pq ja foi validado
        }
    }
    //métodos obrigatorios
    //contrutor de copy 
    public Maze(Maze model) throws Exception{
        if(model == null){ //se o model for nulo lança uma exceçao
            throw new Exception("Modelo ausente");
        }

        this.numberOfColumns = model.numberOfColumns;
        this.numberOfRows  = model.numberOfRows; //aqui o model receberá cada valor do atributo
        this.maze  = model.maze;
    }

      
      public Object Clone(){
        Maze ret = null; //novo atributo ret inicia nulo do tipo maze

        try { 
            ret = new Maze(this); //aqui instancia um novo maze no ret
        } catch (Exception e) {
          System.out.println(e);
        }
        return ret; //depois retorna o ret
    } 
    @Override
    public String toString(){

        String ret = "(Labirinto)\n";

        if(this.maze == null){
            ret = "Labirinto inexistente"; //nao tem nada pra printar, inexistente          
        }
        else {
            for(int i = 0; i <this.numberOfRows; i++ ){ //senao faz um for percorrendo as linhas dessa matriz
                for(int j = 0; j < this.numberOfColumns; j++){ //outro percorrendo as colunas dessa matriz, esses this são sempre os atributos da classes que só são atribuidos depois da validação do construtor
                    ret += Character.toString(this.maze[i][j]);//depois vai somando no ret o maze, para printar pro usuario
                }
                ret += "\n"; 
            }
        }
        return ret; //depois printa pro usuario
    }

    @Override
    public int hashCode(){ //é uma tecnica de espalhamento, cria pequenas listas em vetores, quando multiplica por um primo, ele da um nmr bem grande, que depois joga na posicao do vetor uma lista pequena
        int ret = 7; //depende de um numero primo

        ret = ret * 7 + new Integer(this.numberOfRows).hashCode(); //multiplica cada atributo primitivo da classe por um numero primo, isntanciar ele pra um hashcode
        ret = ret * 7 + new Integer(this.numberOfColumns).hashCode();
        ret = ret * 7 + new Character(this.above).hashCode();
        ret = ret * 7 + new Character(this.below).hashCode();
        ret = ret * 7 + new Character(this.right).hashCode();
        ret = ret * 7 + new Character(this.left).hashCode();

        for (int i =0; i < numberOfRows; i++){ //alem tambem de mulitplicar element a element da matriz
            for (int j =0; j < numberOfColumns; j++){
                ret = ret * 7 + new Character(this.maze[i][j]).hashCode(); //depois multiplica cada element por um numero primo dando um hashcode neles
            }
        }

        if(ret < 0){ //se for menor que zero
            ret = ret - ret; //decrementação no ret
        }
        return ret;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) //se o this tem o msm enderereço que o objeto, entao ele é igual, true
        return true;

        if (obj == null) //se o obj for nulo, voce ta comparando com nada, falso
        return false;

        if (!(obj instanceof Maze))//se o que foi passei for diferente do tipo maze, entao nao é igual, falso
        return false;

        Maze labi = (Maze) obj;//aqui mostro que o obj é do tipo maze

        if(this.numberOfRows != labi.numberOfRows) //se o qtd de linhas da classe for diferente da qtd de linhas desse obj, entao é falso
        return false;

        if(this.numberOfColumns != labi.numberOfColumns) //se a qtd de colunas da classe for diferente da qtd de colunas o obj isntanciado, é diferente
        return false;

        for (int i = 0; i < numberOfRows; i++){ //aqui percorre todas as linhas do maze
            for (int j = 0; j < numberOfColumns; j++){ //aqui as colunas
               if (this.maze[i][j] != labi.maze[i][j]) //aqui comparacao posicao a posicao, se for diferente, nao é o mesmo maze, retorna falso
                    return false;
            }
        }
        return true; //caso contrario, retornara true, ou seja, sao iguais
    }

    public int getNumberOfColumns(){
        return this.numberOfColumns;
    }
    public int getNumberOfRows() {
        return numberOfRows;
    }
    private char getAbove() {
        return this.maze[this.current.getRow() - 1][this.current.getColumn()];
    }
    public char getBelow() {
        return this.maze[this.current.getRow() + 1][this.current.getColumn()];
    }
    public char getRight() {
        return this.maze[this.current.getRow()][this.current.getColumn() + 1];
    }
    public char getLeft() {
        return this.maze[this.current.getRow()][this.current.getColumn() - 1];
    }
    public char getWhereIAm() {
        return this.maze[this.current.getRow()][this.current.getColumn()];
    }
}
