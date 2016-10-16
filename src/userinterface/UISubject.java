/*
* Anon., in Collins, J. (ed.) (2015) ‘CS4125: Serenity Gaming – a sample project from Sem1 2014-2015’,
* CS4125: Systems Analysis & Design [online],
* available: http://www.csis.ul.ie/coursemodule/CS4125 [accessed 20 Oct 2015]
*
*   Adapted file: Serenity_Gaming/Subject.java
*   Approximate use: 70%
**/
package userinterface;

public interface UISubject {

    void registerObserver(Screen scr);

    void removeObserver();

    void notifyObserver();

    Menu getUpdatedMenu();
}
