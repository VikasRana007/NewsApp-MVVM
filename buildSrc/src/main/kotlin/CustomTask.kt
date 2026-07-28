import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class CustomTask : DefaultTask() {

    @TaskAction
    fun executeTask(){
        println("Hello from Custom Task Class!")
    }
}