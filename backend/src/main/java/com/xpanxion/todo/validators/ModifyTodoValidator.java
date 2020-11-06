package com.xpanxion.todo.validators;

import com.xpanxion.todo.domain.TodoEntry;
import com.xpanxion.todo.domain.TodoEntryChanges;
import com.xpanxion.todo.exceptions.ModifyTodoValidatorException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Optional;

    @Service
    public class ModifyTodoValidator {
        public static final int MAX_TEXT_COLUMN_SIZE = 65535;
        public static final int MAX_MEDIUMTEXT_COLUMN_SIZEZ = 16777215;

        public TodoEntryChanges validate(String idString, TodoEntry todoChanges) throws ModifyTodoValidatorException {
            long id = this.validateAndParseID(idString);
            this.validateTitleLength(todoChanges);
            this.validateDescriptionLength(todoChanges);
            this.validateCreatedAtIsISOFormat(todoChanges);
            this.validateDueOnIsISOFormat(todoChanges);
            this.validateCompletedOnIsISOFormat(todoChanges);
            this.validateLastModifiedIsISOFormat(todoChanges);

            Optional<String> maybeTitle = Optional.ofNullable(todoChanges.getTitle());
            Optional<String> maybeDesc = Optional.ofNullable(todoChanges.getDescription());
            Optional<String> maybeDateCreated = Optional.ofNullable(todoChanges.getCreatedAt());
            Optional<Boolean> maybeCompleted = Optional.ofNullable(todoChanges.getCompleted());
            Optional<String> maybeDueOn = Optional.ofNullable(todoChanges.getDueOn());
            Optional<String> maybeCompletedOn = Optional.ofNullable(todoChanges.getCompletedOn());
            Optional<String> lastModified = Optional.ofNullable(todoChanges.getLastModified());

            return new TodoEntryChanges(id, maybeTitle, maybeDesc, maybeDateCreated, maybeDueOn, maybeCompletedOn, lastModified, maybeCompleted);
        }

        private long validateAndParseID(String idString) throws ModifyTodoValidatorException {
            try {
                //turn our string into an ID number
                return Long.parseLong(idString);
            } catch (NumberFormatException ex) {
                throw new ModifyTodoValidatorException("Invalid ID was passed. ID must be a number");
            }
        }

        private void validateTitleLength(TodoEntry todoChanges) throws ModifyTodoValidatorException {
            if (todoChanges.getTitle() !=null && todoChanges.getTitle().length() > MAX_TEXT_COLUMN_SIZE) {
                throw new ModifyTodoValidatorException("Invalid title was passed. Title must be 65,535 characters or less in length");
            }
        }

        private void validateDescriptionLength(TodoEntry todoChanges) throws ModifyTodoValidatorException {
            if (todoChanges.getTitle() != null && todoChanges.getDescription().length() > MAX_MEDIUMTEXT_COLUMN_SIZEZ) {
                throw new ModifyTodoValidatorException("Invalid description waspassed. Description must be 16,777,215 charactors or less in length");
            }
        }
        private void validateCreatedAtIsISOFormat(TodoEntry todoChanges) throws ModifyTodoValidatorException {

        }
        private void validateDueOnIsISOFormat(TodoEntry todoChanges) throws ModifyTodoValidatorException {
            if (todoChanges.getDueOn() != null) {
                if (todoChanges.getDueOn() != null) {
                    try {
                        Instant.parse(todoChanges.getDueOn());
                    } catch (DateTimeParseException ex ) {
                        throw new ModifyTodoValidatorException("Invalid due at date was passed. Dates must be in ISO Format YYYY-MM-DD'T'HH:mm:ss.SSSZ");
                    }
                }
            }
        }
        private void validateCompletedOnIsISOFormat(TodoEntry todoChanges) throws ModifyTodoValidatorException {
            if (todoChanges.getCompletedOn() != null) {
                if (todoChanges.getCompletedOn() != null) {
                    try {
                        Instant.parse(todoChanges.getCompletedOn());
                    } catch (DateTimeParseException ex) {
                        throw new ModifyTodoValidatorException("Invalid Completed On date was passed. Dates must be in ISO format YYYY-MM-DD`T`HH:mm:ss.SSSZ");
                    }
                }
            }
        }

        private void validateLastModifiedIsISOFormat(TodoEntry todoChanges) throws ModifyTodoValidatorException {
            if (todoChanges.getLastModified() != null) {
                if (todoChanges.getLastModified() != null) {
                    try {
                        Instant.parse(todoChanges.getLastModified());
                    } catch (DateTimeParseException ex) {
                        throw new ModifyTodoValidatorException("Invalid last modified at date was passed. Dates must be in ISO format YYYY-MM-DD`T`HH:mm:ss.SSSZ");
                    }
                }
            }
        }
    }