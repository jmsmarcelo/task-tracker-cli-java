import java.io.IOException;

public class TaskCli {
    private static String tableLine = "%1$s────────%2$s────────────────────────────────────────────────────%2$s──────────────%2$s─────────────────────%2$s─────────────────────%3$s";
    private static final TaskService service = new TaskService();

    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("no command found\nuse 'help' for a list of commands");
            return;
        }
        switch(args[0]) {
            case "add" -> handleAddCommand(args);
            case "update" -> handleUpdateCommand(args);
            case "delete" -> handleDeleteCommand(args);
            case "mark-in-progress" -> handleMarkAsCommand(args, TaskStatus.IN_PROGRESS);
            case "mark-done" -> handleMarkAsCommand(args, TaskStatus.DONE);
            case "list" -> handleListCommand(args);
            case "help" -> handleHelpCommand();
            default -> System.out.println("invalid command\nuse 'help' for a list of commands");
        }
    }
    private static void handleAddCommand(String[] args) {
        if(args.length != 2) {
            System.out.print("""
                invalid command
                usage:      add <description>
                example:    add "Buy groceries"
                """);
            return;
        }
        if(args[1].isBlank()) {
            System.out.println("Task no description is not allowed");
            return;
        }
        try {
            Long id = service.add(args[1].replaceAll("[\\\\\"]+", ""));
            if(id == 0) {
                System.out.println("no Task found for add");
            } else {
                System.out.println("Task added successfully (ID: %d)".formatted(id));
            }
        } catch (IOException e) {
            System.out.print("""
                error:              could not add the Task to data file
                possible reason:    no permission to write to file
                """);
        }
    }
    private static void handleUpdateCommand(String[] args) {
        if(args.length != 3 || !args[1].matches("\\d+")) {
            System.out.print("""
                invalid command
                usage:      update <id> <description>
                example:    update 1 "Buy groceries and cook dinner"
                """);
            return;
        }
        if(args[2].isBlank()) {
            System.out.println("Task no description is not allowed");
            return;
        }
        try {
            if(service.update(Long.parseLong(args[1]), args[2])) {
                System.out.println("Task updated successfully");
            } else {
                System.out.println("Task(ID: %s) not found".formatted(args[1]));
            }
        } catch (NumberFormatException | IOException e) {
            System.out.print("""
                error:              Could not update the Task to data file
                possible reason:    no permission to read/write to file
                """);
        }
    }
    private static void handleDeleteCommand(String[] args) {
        if(args.length != 2 || !args[1].matches("\\d+")) {
            System.out.print("""
                invalid command
                usage:      delete <id>
                example:    delete 1
                """);
            return;
        }
        try {
            if(service.delete(Long.parseLong(args[1]))) {
                System.out.println("Task deleted successfully");
            } else {
                System.out.println("Task(ID: %s) not found".formatted(args[1]));
            }
        } catch (Exception e) {
            System.out.print("""
                error:              could not delete the Task to data file
                possible reason:    no permission to read/write to file
                """);
        }
    }
    private static void handleMarkAsCommand(String[] args, TaskStatus status) {
        if(args.length != 2 || !args[1].matches("\\d+")) {
            System.out.print("""
                invalid command
                usage:      %1$s <id>
                example:    %1$s 1
                """.formatted(args[0]));
            return;
        }
        try {
            if(service.update(Long.parseLong(args[1]), status)) {
                System.out.println("Task marked as %s successfully"
                    .formatted(args[0].replace("mark-", "")));
            } else {
                System.out.println("Task(ID: %s) not found".formatted(args[1]));
            }
        } catch (NumberFormatException | IOException e) {
            System.out.print("""
                error:              could not mark the Task as %s in the data file
                possible reason:    no permission to read/write to file
                """.formatted(args[0].replace("mark-", "")));
        }
    }
    private static void handleListCommand(String[] args) {
        try {
            if(args.length == 1) {
                printTable(true);
                service.find("all").forEach(TaskCli::printTable);
                printTable(false);
            } else if(args.length == 2 && TaskStatus.isValid(args[1])) {
                printTable(true);
                service.find(args[1]).forEach(TaskCli::printTable);
                printTable(false);
            } else {
                System.out.print("""
                    invalid command
                    usage:      list [%s]
                    example:    list
                                list done
                    """.formatted(TaskStatus.valuesJoin("|")));
            }
        } catch (IOException e) {
            System.out.print("""
                error:              could not load the Tasks from data file
                possible reason:    no permission to read to file
                """);
        }
    }
    private static void printTable(boolean header) {
        if(header) {
            System.out.println(tableLine.formatted("┌", "┬", "┐"));
            System.out.printf("│ %-6s │ %-50s │ %-12s │ %-19s │ %-19s │\n", "  ID", "                   Description", "   Status", "     CreatedAt", "     UpdatedAt");
        } else {
            System.out.println(tableLine.formatted("└", "┴", "┘"));
        }
    }
    private static void printTable(Task task) {
        System.out.println(tableLine.formatted("├", "┼", "┤"));
        System.out.printf("│ %-6d │ %-50s │ %-12s │ %-19s │ %-19s │\n", task.getId(), task.getDescription(), task.getStatus(), task.getCreatedAt(), task.getUpdatedAt());
    }
    private static void handleHelpCommand() {
        System.out.println("----------------------------- Task Tracker CLI - Help -----------------------------");
        System.out.println("Available commands:");
        System.out.println("    add <description>           - add a new task with the given description");
        System.out.println("    update <id> <description>   - Update the task with the given ID");
        System.out.println("    delete <id>                 - Delete the task with the given ID");
        System.out.println("    mark-in-progress <id>       - Mark as 'in-progress' the task with the given ID");
        System.out.println("    mark-done <id>              - Mark as 'done' the task with the given ID");
        System.out.println("    list                        - List all tasks");
        System.out.println("    list todo                   - List all tasks with status 'todo'");
        System.out.println("    list in-progress            - List all tasks with status 'in-progress'");
        System.out.println("    list done                   - List all tasks with status 'done'");
        System.out.println("    help                        - Display this help message");
        System.out.println();
        System.out.println("Example usage:");
        System.out.println("    java TaskCli add \"Buy groceries\"");
        System.out.println("    java TaskCli update 1 \"Buy groceries and cook dinner\"");
        System.out.println("    java TaskCli delete 1");
        System.out.println("    java TaskCli mark-in-progress 1");
        System.out.println("    java TaskCli list done");
        System.out.println("-----------------------------------------------------------------------------------");
    }
}