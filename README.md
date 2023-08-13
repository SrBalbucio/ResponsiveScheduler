[![](https://jitpack.io/v/SrBalbucio/ResponsiveScheduler.svg)](https://jitpack.io/#SrBalbucio/ResponsiveScheduler)
# ResponsiveScheduler
Agende tarefas em Java de forma rápida e segura.<br>Schedule tasks in Java quickly and securely.

## Primeiros passos
Adicione o ResponsiveScheduler ao seu projeto e crie uma instância como feito abaixo:
```java
ResponsiveScheduler scheduler = new ResponsiveScheduler();
```
Se desejar receber e manipular os eventos do scheduler crie uma classe Listener e adicione ao EventManager:
```java
ClasseListener listener = new ClasseListener();
scheduler.getEventManager().registerListener(listener);
```
Após isso você está pronto para criar tarefas.

## Crie tarefas
Para criar uma tarefa utilizamos o RSTask, que é uma implementação do Runnable para ResponsiveScheduler.
```java
// Esta classe extende a RSTask
TaskTest test = new TaskTest();

// CLASSE TaskTest

public class TaskTest extends RSTask {
    @Override
    public void run() {
        System.out.println("Estou rodando!");
    }
}
```
Dentro de uma classe RSTask você pode usar o método ``setProblem(true)`` para dizer ao ResponsiveScheduler que algo deu erro na tarefa.

## Rode as tarefas
Há várias formas de rodar uma tarefa, você repetir ela várias vezes, executar após um tempo ou apenas executar de forma async.<br>Apesar dos nomes todas as tarefas rodam fora de thread principal, ou seja, async
<br><br>
Rodar uma tarefa no pool de threads (a task entrará numa fila de execuções): 
```java
// adicione a task
scheduler.runTask(test); // isso executará a task
```
Rodar uma tarefa numa thread dedicada:
```java
// adicione a task
scheduler.runAsyncTask(test); // isso executará a task
```
Rodar uma tarefa após um tempo:
```java
// adiciona a tarefa e o tempo em millisegundos
scheduler.runTaskAfter(test, 2000); // isso executará a task após 2 segundos
```
Rodar uma tarefa de tempos em tempos:
```java
// adiciona a tarefa e o tempo/delay em millisegundos
scheduler.repeatTask(test, 0, 2000); // executa a task sem delay (0) a cada 2 segundos
```
## Cancele tarefas
Para cancelar tarefas é muito simples, use o método ``cancelTask(task);`` para cancelar uma tarefa especifica ou use ``cancelAllTasks();`` para cancelar todas.
## Finalizar
Para finalizar o ResponsiveScheduler é necessário usar a função ``shutdown();``, essa função irá finalizar todas as tarefas e liberar todas as threads usadas. Obs.: Serão emitidos eventos de cancelamento quando as tarefas forem finalizadas pelo shutdown.
