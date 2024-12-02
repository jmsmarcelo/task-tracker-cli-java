# Task Tracker CLI

A command-line application to track and manage tasks, developed in **Java** as part of the [Task Tracker Project](https://roadmap.sh/projects/task-tracker) from **Roadmap.sh**.

Version with GUI (JavaFX): [Task Tracker GUI](https://github.com/jmsmarcelo/task-tracker-javafx)

## 📋 Features
- Add, Update, and Delete tasks
- Mark a task as in progress or done
- List all tasks
- List all tasks are not done
- List all tasks are in progress
- List all tasks are done

## ⚙️ Technologies and Tools

- Language: **Java**
- CLI Structure: Input/output handling
- Data Persistence: Store the tasks in a JSON file

## 🛠️ How to Run the Project

### Prerequisites

- **Java 17** or later installed
- Read/write permission

### Steps to Execute

1. Clone the repository:
  ```bash
  git clone https://github.com/jmsmarcelo/task-tracker-cli-java.git
  cd task-tracker-cli-java
  ```
2. Compile the project:
   ```bash
   mkdir bin
   javac -d bin src/*.java
   ```
3. Run the project:
   ```bash
   cd bin
   java TaskCli help
   ```
Alternatively, use automatic compile and run
   - Windows
     - Double click in `win_compile.bat` `win_run.bat`
     
   - Linux/MacOS
     ```bash
     # Make scripts executable
     chmod +x unix_compile.sh unix_run.sh

     # Run the scripts
     ./unix_compile.sh
     ./unix_run.sh
     ```

## 📌 Usage
   ```bash
# Adding a new task
java TaskCli add "Buy groceries"
# output: Task added successfully (ID: 1)

# Updating task
java TaskCli update 1 "Buy groceries and cook dinner"
# output: Task updated successfully

# Deleting task
java TaskCli delete 1
# output: Task deleted successfully

# Marking a task as in progress
java TaskCli mark-in-progress 1
# output: Task marked as in-progress successfully

# Marking a task as done
java TaskCli mark-done 1
# output: Task marked as done successfully

# Listing all tasks
java TaskCli list
# output: id: 1, description: Buy groceries and cook dinner, status: todo, createdAt: 2024-11-28T06:39:51.227335800, updatedAt: 2024-11-28T06:40:40.429241100

# Listing tasks by status
java TaskCli list todo
java TaskCli list in-progress
java TaskCli list done
   ```

## 📦 Project Structure
```plaintext
task-tracker-cli-java/
└─ src/
   ├─ TaskCli.java         # Application entry point
   ├─ Task.java            # Task domain model
   ├─ TaskStatus.java      # Enum task status
   ├─ TaskService.java     # Task service logic
   └─ TaskRepository.java  # file persistence handling
```

## 📖 How to Contribute

Contributions are welcome!

To contribute:
1. Fork the repository.
2. Create a new branch:
   ```bash
   git checkout -b my-new-feature
   ```
3. Commit your changes:
   ```bash
   git commit -m "add new feature"
   ```
4. Push to the branch:
   ```bash
   git push origin my-new-feature
   ```
5. Open a Pull Request.

## 📜 License
This project is licensed under the **MIT License**. See the [LICENSE](https://github.com/jmsmarcelo/task-tracker-cli-java/blob/main/LICENSE) file for details.

## 🙋‍♂️ Author
Created by [Jose Marcelo](https://jmsmarcelo.github.io/). Feel free to get in touch or explore more of my projects!
