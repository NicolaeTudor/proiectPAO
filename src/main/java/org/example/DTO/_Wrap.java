package org.example.DTO;

//https://stackoverflow.com/a/431152
public class _Wrap<E> {
    E ref;
    public _Wrap(E e ){
        ref = e;
    }
    public E get() { return ref; }
    public void set( E e ){ this.ref = e; }

    public String toString() {
        return ref.toString();
    }
}