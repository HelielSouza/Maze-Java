public class Coordinate implements Cloneable{
    
    private int x; //row
    private int y; //column

    public Coordinate(int row, int column) throws Exception{ //construtor

        if (row < 0 || column < 0) // caso a row ou a column for menor que zero
            throw new Exception("Coordenadas invalidas"); // é lançada essa exceção

        this.x = row; //caso seja valido o valor da row é atribuido a variavel no local x
        this.y = column; //atribuição de column para y
    }

    //setters
    public void setRow(int row) throws Exception{ 
        if (row < 0) // se o valor for menor que zero
            throw new Exception("Linha inválida"); // lança essa exceção
    
        this.x = row; //atribuição
    }
    public void setColumn(int column) throws Exception{
        if (column < 0)
        throw new Exception("Coluna invalida");

    this.y = column;
    }

    //getters
    public int getRow() { 
        return x;
    }
    public int getColumn() {
        return y;
    }

    //hashcode
    public int hashCode(){
        int ret = 13; //variável ret com número primo

        ret = ret * 13 + new Integer(this.x).hashCode(); //instacia do valor x com o metodo hash, onde cria pequenas listas em vetores
        ret = ret * 13 + new Integer(this.y).hashCode();
 
        if(ret < 0) //caso dê esse valor, que não existe no vetor
            ret = ret - ret; //é feita essa opração para positivar esse valor para inserir a lista no vetor
        return ret;
    }

    //Equals
    public boolean equals(Object obj) {

        if (this == obj) //se o endereço for igual do obj, true, estou no msm objeto
        return true;

        if (obj == null) //se for nulo, false, nao ta comparando com nada
        return false;

        if (!(obj instanceof Coordinate)) //se o que passei pelo paramatro for diferente do tipo da classe Coordinate, false
        return false;

        Coordinate coord = (Coordinate) obj; //objeto do tipo Coordinate

        if(this.x != coord.x)//se o this.x(objeto atributo global) for diferente do coord que passei pelo parametro, false
        return false;

        if(this.y != coord.y)//se o this.y(objeto atributo global) for diferente do coord que passei pelo parametro, false
        return false;

        return true; //se nada acontecer, são exatamente iguais, TRUE
    }

    //toString
    public String toString(){

        String ret;

        ret = "(";

        ret += this.x + "," + this.y;

        ret +=")"; //para englobar os valores das coordenadas em parenteses, ex:"(x,y)"
        
        return ret;
    }

    //Clone da classe Coordinate
    public Coordinate(Coordinate model) throws Exception{ //lança exceção

        if (model == null) // se for nulo, lança exceção
            throw new Exception("Modelo invalido");
        
        this.x = model.x; //senão, esse model vai receber o valor do atributo row e column dessa classe 
        this.y = model.y;
    }

    //Object clone
    public Object clone(){

        Coordinate ret = null; //é do tipo Coordinate que recebe nula

        try {
            ret = new Coordinate(this); //ele vai tentar instanciar sempre o valor do this, ao que se refere ao atributo dessa classe
        } catch (Exception e) {
        }
        return ret;
    }
}
