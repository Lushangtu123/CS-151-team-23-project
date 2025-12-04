package cs151.controller.services;

import cs151.model.Student;

public interface ActionsHandler<T> {
    void handleView(T item, Runnable onClose);
    void handleEdit(T item);
    boolean handleDelete(T item);
}
