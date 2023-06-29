import java.lang.reflect.*;

public class Queue <X> implements Cloneable
{
    private Object[] element; //private X[] element;
    private int      initialSize;
    private int      last=-1; //vazio

    public Queue(int size) throws Exception
    {
        if (size<=0)
            throw new Exception ("Tamanho inválido");

        this.element       = new Object [size]; //this.element=new X [size];
        this.initialSize = size;
    }

    public Queue ()
    {
        this.element       = new Object [10]; //this.element=new X [10];
        this.initialSize = 10;
    }

    private void resize (float factor)
    {
        // X[] novo = new X [Math.round(this.element.length*factor)];
        Object[] newSize = new Object [Math.round(this.element.length*factor)];

        for(int i=0; i<=this.last; i++)
        newSize[i] = this.element[i];

        this.element = newSize;
    }

    private X myXClone (X x)
    {
        X ret=null;

        try
        {
            Class<?> classe         = x.getClass();
            Class<?>[] tipoDosParms = null;
            Method meth             = classe.getMethod("clone",tipoDosParms);
            Object[] parms          = null;
            ret                     = (X)meth.invoke(x,parms);
        }
        catch(NoSuchMethodException erro)
        {}
        catch(IllegalAccessException erro)
        {}
        catch(InvocationTargetException erro)
        {}

        return ret;
    }

    public void SaveAnItem (X x) throws Exception // LIFO
    {
        if (x==null)
            throw new Exception ("Falta o que guardar");

        if (this.last+1==this.element.length) // cheia
            this.resize (2.0F);

        this.last++;

        if (x instanceof Cloneable)
            this.element[this.last]=myXClone(x);
        else
            this.element[this.last]=x;
    }

    public X getAItem () throws Exception // LIFO
    {
        if (this.last==-1) // vazia
            throw new Exception ("Nada a recuperar");

        X ret=null;
        if (this.element[this.last] instanceof Cloneable)
            ret = myXClone((X)this.element[0]);
        else
            ret = (X)this.element[0];

        return ret;
    }

    public void removeAItem () throws Exception // LIFO
    {
        if (this.last==-1) // vazia
            throw new Exception ("Nada a remover");

        for(int i =0; i <= this.last -1; i++)
        {
            this.element[i] = this.element[i + 1];
        }

        this.element[this.last] = null;
        this.last--;

        if (this.element.length>this.initialSize &&
                this.last+1<=Math.round(this.element.length*0.25F))
            this.resize (0.5F);
    }

    public boolean isFull ()
    {
        if(this.last+1==this.element.length)
            return true;

        return false;
    }

    public boolean isEmpty ()
    {
        if(this.last==-1)
            return true;

        return false;
    }

    public String toString ()
    {
        String ret = (this.last+1) + " elemento(s)";

        if (this.last!=-1)
            ret += ", sendo o ultimo "+this.element[this.last] + ", sendo o primeiro "+this.element[0];

        return ret;
    }

    public boolean equals (Object obj)
    {
        if(this==obj)
            return true;

        if(obj==null)
            return false;

        if(this.getClass()!=obj.getClass())
            return false;

        Queue<X> fil = (Queue<X>) obj;

        if(this.last!=fil.last)
            return false;

        if(this.initialSize!=fil.initialSize)
            return false;

        for(int i=0 ; i<this.last;i++)
            if(!this.element[i].equals(fil.element[i]))
                return false;

        return true;
    }

    public int hashCode ()
    {
        int ret=666/*qualquer positivo*/;

        ret = ret*7/*primo*/ + Integer.valueOf(this.last        ).hashCode();
        ret = ret*7/*primo*/ + Integer.valueOf(this.initialSize).hashCode();

        for (int i=0; i<this.last; i++)
            ret = ret*7/*primo*/ + this.element[i].hashCode();

        if (ret<0)
            ret=-ret;

        return ret;
    }

    // construtor de copy
    public Queue (Queue<X> model) throws Exception
    {
        if(model == null)
            throw new Exception("Modelo ausente");

        this.initialSize = model.initialSize;
        this.last         = model.last;

        // para fazer a copy dum vetor
        // precisa criar um vetor novo, com new
        // nao pode fazer this.element=model.element
        // pois se assim fizermos estaremos com dois
        // objetos, o this e o model, compartilhando
        // o mesmo vetor
        this.element = new Object[model.element.length]; // this.element = new X [model.element.length];

        for(int i=0 ; i<model.element.length ; i++)
            this.element[i] = model.element[i];
    }

    public Object clone ()
    {
        Queue<X> ret=null;

        try
        {
            ret = new Queue<X>(this);
        }
        catch(Exception erro)
        {}

        return ret;
    }
}