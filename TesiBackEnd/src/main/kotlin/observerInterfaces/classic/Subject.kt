package observerInterfaces.classic

interface Subject {
    fun attach(o: Observer)
    fun detach(o: Observer)
    fun notifyObservers()
}