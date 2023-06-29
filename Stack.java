public class Stack<X> implements Cloneable { //x é o parametro generico para criar uma pilha de qualquer tipo possivel, seja int, string, etc   
    
    private Object[] element; //do tipo object
    private int initialSize;
    private int last = -1; //estando negativo, está vazio

    //CONSTRUTOR
    public Stack(int size) throws Exception{ //construtor padrão
        if (size <= 0){ //verifica se o size é menor ou igual a 0, se for ele lança uma exceção
            throw new Exception("Tamanho invalido");
        }

        this.element = new Object[size]; //caso nao seja, o atributo local element recebe um novo objeto com o size do vetor, atribuito pelo parametro do construtor.
        this.initialSize = size; // e tambem é atribuído ao atributo local "initialSize", o valor do parametro "size"
    }


//aqui haverá um size padrão
    public Stack(Stack<X> pilha){ 
        this.element = new Object[10]; //se for menor que 10, automaticamente sera atribuito um valor de 10
          
        this.initialSize = 10;
    }

    public int getQuantidade(){
        return this.last + 1; //adiciona um element ao last, afinal ela nao estara vazia
    }



    public void resize(float factor){ // caso o parametro da pilha que for passado for maior que o padrão, usa-se esse metodo
        Object[] novo = new Object[Math.round(this.element.length * factor)]; // é criado um novo objeto que receberá uma função, que calcula o size da pilha e esse objeto é criado com o factor multiplicativo
//esse factor multiplicativo se encontra no metodo abelow, que no caso é dois, após passar pela verificação

        for (int i = 0; i <= this.last; i++) {
            novo[i] = this.element[i]; //nesse for, o objeto "novo" já com o size dobrado, receberá os elementos do objeto anterior
        }

        this.element = novo; //em seguida, o objeto anterior receberá um novo objeto, como o novo size redmensionado
    }




    //LIFO - last in first out
    public void SaveAnItem(X x) throws Exception{ // aqui recebe como parametro um objeto da classe generica
        if (x == null) //se o parametro for nulo dispara a exceção abelow
            throw new Exception("Valor ausente");
            
        if (this.last + 1 == this.element.length) //ele verifica se o atributo 'last' é do size do element, ou seja, da pilha
            this.resize(2.0F); //se for, ele passa como parametro do metodo redimensione-se aabove, o valor 2. afinal ele precisa redimensionar pq a pilha esta cheia, dobrando o size dela

        this.last++;
        this.element[this.last] = x;
    }

    public X getAItem() throws Exception{ //nesse metodo ele irá retornar um item da pilha do tipo X, no caso o X pode representar as coordenadas
        if (this.last == -1) //se for isso, é pq nao foi colocado nenhum element na pilha, lançando a exceção
            throw new Exception("Pilha vazia");
        
        return (X) this.element[this.last]; //caso tenha passado da validação, ele retornará o last item da pilha, que foi o last a ser colocado na pilha. ou seja, a ultima posicao do vetor do element
        //aabove ele é visto como object, então ele é forçado a ser visto como tipo X.
        //esse metodo é como se fosse um get
    } 

    //LIFO
    public void removeAItem() throws Exception{ //
        if(this.last == -1) //verifica se está vazia, se tiver lança a exceção
            throw new Exception("Nada a remover");
        
        this.element[this.last] = null; //o last element da pilha, será atribuida um valor nulo a ela
        this.last --; //esse -- é a posição da pilha, removemendo a posição, decrementando

        if(this.element.length > this.initialSize && this.last + 1 <= Math.round(this.element.length * 0.25F)){
        //se o size do element for maior que o size inical E o last element + 1 for menor ou igual ao size size da pilha *0.25
            this.resize(0.5F); // ele fara o redmensionamento pela metade
        }
    }

    public boolean isFull(){
        return this.last + 1 == this.element.length; // se o atributo last+1 for igual ao size da pilha, retornará verdadeiro
    }

    public boolean isEmpty(){ //se o last for - 1, é verdadeira
        return this.last == -1;
    }

    @Override
    public String toString(){
        String ret;

        if (this.last == 0)
            ret = "1 elemento";
        
        else
            ret = (this.last + 1) + " elementos";
        
        if (this.last != -1)
            ret += ", sendo o ultimo" + this.element[this.last];
        
        return ret;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) //compara o endereço do chamante 'this' com o objeto, entao true
        return true;

        if (obj == null) //se obj nao tem valor, ele é nulo, vazio, entao é false
        return false;

        if (this.getClass() != obj.getClass())//ele compara se o obj é da mesma classe que pilha, se for de classe diferente, nao tem como ser igual, dando false
        return false;

        //se ele não for do mesmo endereço, não for nulo e for da mesma classe, ele continua o programa

        Stack<X> pil = (Stack<X>) obj; // entao a pil, é do mesmo tipo do obj

        if(this.last != pil.last) //se o valor do atributo local last for diferente do valor do pil.last, então nao sao mesma pilha, por ex: um pilha tem element(1,2,4) e a outra (1,2,3), são diferentes
        return false;

        if(this.initialSize != pil.initialSize) // se o size inicial de ambos são diferentes, não são a mesma pilha
        return false;       //retornando falso

        for (int i = 0; i <= this.last; i++) {
            if(!this.element[i].equals(pil.element[i])) //aqui ele compara element a element da pilha , vendo se ele são iguais, não é recursão
                return false;
        }

        return true; // se nenhuma validação for aceita, retorna true pq sao duas pilhas iguais
        //essa pilha é mt usada pq a Coordinate que usamos no maze entra no equals
    }
    
    //Clone
    @Override
    public Object clone(){
        Stack<X> ret = null; // ele vai ser um objeto do tipo generico com valor nulo

        try{
            ret = new Stack<X>(this); //ele vai tentar retornar o this, com o atributo da pilha, que é o clone dela, aonde ele é guardade no SaveAnItem que está comentado
        }catch(Exception erro){}
        return ret;
    }

    //Metodo Hash
    @Override
    public int hashCode(){
        int ret = 666; // qualquer positivo

        ret = ret * 7 + new Integer(this.last).hashCode(); // o ret recebe o ret multiplicado por um número primo e dar um hashcode nela
        ret = ret * 7 + new Integer(this.initialSize).hashCode(); // quando fazemos isso, criamos pequenas listinhas em vetores, ótimo para facilitar a busca

        for (int i = 0; i <= this.last; i++) { //abelow acontece a mesma coisa
            ret = ret * 7 + element[i].hashCode(); //onde multiplica cada element da pilha por um primo, atribuindo a hashcode
        }
 
        if(ret < 0) //caso seja negativo
            ret = -ret; //é atribuido a eles um valor negativo para ele ficar positivo
        return ret;//no final é retornado o valor ret, para atribuir a lista nos vetores
    }


}
