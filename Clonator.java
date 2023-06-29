import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Clonator<X>{
    public X clone (X x){ //metodo clone do tipo X (classe generico) com parametro da classe do tipo generico

        Class <?> classe = x.getClass();//primeiro passo é obter a classe da instancia no objeto x, que representa a classe X e armazena nesse objeto o objeto da classe

        Class <?>[] tpsParmsForms = null; //aqui ocorre uma instanciação diferente, pq nao ta sendo passado nenhum parametro no vetor, por conta disso nao pode ficar vazio
        //esse preenchimento será feito com o nulo.

        Method meth = null; //aqui temos o metodo clone e por não haver parametro é necessario atribuir nulo a ele tbm


        try {//aqui a classe é armazenada no objeto classe, por isso representa a classe X

            meth = classe.getMethod("clone", tpsParmsForms); //o metodo que estava nulo recebe o getMethod cpm seus parametros 
        } catch (NoSuchMethodException erro) {
        }

        Object[] parmsReais = null; //aqui o objeto parametrosreais recebe nulo, chamaremos o metodo sem parametros. o vetor nulo de ParmsReais é fornecido o resultado da chamada que é classe X. porem tem seu tipo alterado para x

        X ret = null; //o metodo é armazenado no objeto chamado, então faz com que o objeto chamado X seja para ele o chamador, o this dele.
        try {
            ret = (X)meth.invoke(x, parmsReais); //retorna o ret depois de receber o metodo com os parametros
        } catch (InvocationTargetException erro) 
        {}
        catch(IllegalAccessException erro) 
        {}

        return ret;
    }
    //valido lembrar que esse clonador guarda um item comum como o idPrincipal e uma cópia desse item. entao quando o usuario setar o valor ele setara o valor da copy e nao do item principal, do clone principal.
}
