package xyz.twbkg.stock.application

interface BasePresenter<T> {

    /**
     * Drops the reference to the view when destroyed
     */
    fun dropView()

}