package com.lambdaschool.bookstore.services;

import com.lambdaschool.bookstore.BookstoreApplication;
import com.lambdaschool.bookstore.exceptions.ResourceNotFoundException;
import com.lambdaschool.bookstore.models.Book;
import com.lambdaschool.bookstore.models.Section;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookstoreApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//**********
// Note security is handled at the controller, hence we do not need to worry about security here!
//**********
public class BookServiceImplTest
{

    @Autowired
    private BookService bookService;

    @Autowired
    private SectionService sectionService;

    @Before
    public void setUp() throws
            Exception
    {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws
            Exception
    {
    }

    @Test
    public void a_findAll()
    {
        assertEquals(5, bookService.findAll().size());
    }

    @Test
    public void b_findBookById()
    {
        assertEquals("Digital Fortess", bookService.findBookById(27).getTitle());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void c_notFindBookById()
    {
        assertEquals("Digital Fortess", bookService.findBookById(555).getTitle());
    }

    @Test
    public void d_delete()
    {
        bookService.delete(27);
        assertEquals(4, bookService.findAll().size());
    }

    @Test
    public void e_save()
    {
        Book b1 = new Book();
        b1.setSection(sectionService.findSectionById(22));
        b1.setTitle("Mock");
        b1.setIsbn("44448888");
        b1.setCopy(2022);
        Book addBook = bookService.save(b1);
        assertEquals("Mock", addBook.getTitle());
    }

    @Test
    public void f_update()
    {
        String mockName = "Mock Name";
        Book b1 = new Book();
        b1.setBookid(30);
        b1.setTitle(mockName);
        Book updatedBook = bookService.update(b1, 30);
        assertEquals(mockName, updatedBook.getTitle());
    }

    @Test
    public void g_deleteAll()
    {
        bookService.deleteAll();
        assertEquals(0, bookService.findAll().size());
    }
}