package observerInterfaces

interface Subject {
    fun attach(o: Observer)
    fun detach(o: Observer)
}