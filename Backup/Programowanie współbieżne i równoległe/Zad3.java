

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Czytelnia {
    
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(true); 
    private int nr; 
	
    public int czytaj() {

        rwl.readLock().lock();
        try {
            if (nr == 0) 
            {
                System.out.println(Thread.currentThread().getName() + " czeka na wydanie");
            }
            else 
                {
            
                System.out.println(Thread.currentThread().getName() + " czyta... Książkę nr: " + nr);
                return nr;
                }
        }finally {
       rwl.readLock().unlock();
        }
        
        return nr;
    }
	
    public void pisz(int numer){
            rwl.writeLock().lock();
          try { 
              System.out.println(Thread.currentThread().getName() + " pisze : " + "nr: "+ numer  );
              try { 
                  Thread.sleep(2000);
              } catch (InterruptedException e) { System.out.println("Błąd"); }
              nr = numer;
              
          } finally {
               rwl.writeLock().unlock();   
           
        }
        
    }   
}

class Pisarz implements Runnable {
    private Czytelnia p;
       
    Pisarz(Czytelnia p) {
        this.p = p;	
    }
    public void run() {
        while(true) 
        {
            p.pisz( (int)( (Math.random() * 10000 ) + 1) );
             try{
                Thread.sleep(10000);    
            } catch (InterruptedException e) { System.out.println("Błąd"); }
	}
    }
}

class Czytelnik implements Runnable {
    private Czytelnia p;
    Czytelnik(Czytelnia p) {
        this.p = p;
    }
    public void run() {
        while(true)
        {
            
            try{
                 
                p.czytaj();
                Thread.sleep((int)(Math.random()*2000));
            } catch (InterruptedException e) { System.out.println("Błąd"); }
        }
    }
}

public class Zad3 {
	public static void main(String args[]) {
            Czytelnia polka = new Czytelnia();
            
            Pisarz p = new Pisarz(polka);
            Pisarz p1 = new Pisarz(polka);
            Pisarz p2 = new Pisarz(polka);
            Czytelnik k1 = new Czytelnik(polka);
            Czytelnik k2 = new Czytelnik(polka);
            Czytelnik k3 = new Czytelnik(polka); 
            
            Thread pTh = new Thread(p, "Pisarz 1");
            Thread pTh2 = new Thread(p2, "Pisarz 3");
            Thread pTh1 = new Thread(p1, "Pisarz 2");
            Thread cTh = new Thread(k1, "Czytelnik 1");
            Thread cTh1 = new Thread(k2, "Czytelnik 2");
            Thread cTh2 = new Thread(k3, "Czytelnik 3");
             
            pTh.start();
            cTh.start();
            pTh1.start();
            pTh2.start();
            cTh1.start();
            cTh2.start();

        }
}