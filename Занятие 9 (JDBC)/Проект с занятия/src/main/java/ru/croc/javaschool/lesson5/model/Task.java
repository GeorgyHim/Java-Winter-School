package ru.croc.javaschool.lesson5.model;

/**
 * Задача.
 *
 * @author VKhlybov
 */
public class Task {

    /**
     * Идентификатор.
     */
    private Integer id;

    /**
     * Наименование.
     */
    private String title;

    public Task(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
