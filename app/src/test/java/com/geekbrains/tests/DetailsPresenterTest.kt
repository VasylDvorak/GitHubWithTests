package com.geekbrains.tests


import com.geekbrains.tests.presenter.details.DetailsPresenter
import com.geekbrains.tests.view.details.ViewDetailsContract
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

//Тестируем наш Презентер
class DetailsPresenterTest {
    private var count: Int = 0
    private lateinit var presenter: DetailsPresenter

    @Mock
    private lateinit var viewContract: ViewDetailsContract

    @Before
    fun setUp() {
        //Обязательно для аннотаций "@Mock"
        //Раньше было @RunWith(MockitoJUnitRunner.class) в аннотации к самому классу (SearchPresenterTest)
        MockitoAnnotations.initMocks(this)
        //Создаем Презентер, используя моки Репозитория и Вью, проинициализированные строкой выше
        presenter = DetailsPresenter(count).apply {
            viewContract=this@DetailsPresenterTest.viewContract
        }
    }

    @Test
    fun setCounter() {
        count = 10
        presenter.setCounter(count)
        assertEquals(presenter.count, count)
    }

    @Test
    fun onIncrement() {
        presenter.count = count
        presenter.onIncrement()
        verify(viewContract, times(1)).setCount(count + 1)
    }

    @Test
    fun onDecrement() {
        presenter.count = count
        presenter.onDecrement()
        verify(viewContract, times(1)).setCount(count - 1)
    }

    @Test
    fun onAttach() {
        presenter.viewContract = null
        presenter.onAttach(viewContract)
        assertNotNull(presenter.viewContract)
    }

    @Test
    fun onDetach() {
        presenter.viewContract = viewContract
        presenter.onDetach()
        assertNull(presenter.viewContract)
    }
}
