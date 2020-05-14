package com.expediagroup.graphql.examples.client

import com.expediagroup.graphql.client.GraphQLClient
import com.expediagroup.graphql.generated.AddObjectMutation
import com.expediagroup.graphql.generated.HelloWorldQuery
import com.expediagroup.graphql.generated.RetrieveObjectQuery
import com.expediagroup.graphql.generated.UpdateObjectMutation
import kotlinx.coroutines.runBlocking
import java.net.URL

fun main() {
    val client = GraphQLClient(url = URL("http://localhost:8080/graphql"))
    val helloWorldQuery = HelloWorldQuery(client)
    println("HelloWorld examples")
    runBlocking {
        val helloWorldResultNoParam = helloWorldQuery.execute(variables = HelloWorldQuery.Variables(name = null))
        println("\tquery without parameters result: ${helloWorldResultNoParam.data?.helloWorld}")

        val helloWorldResult = helloWorldQuery.execute(variables = HelloWorldQuery.Variables(name = "Dariusz"))
        println("\tquery with parameters result: ${helloWorldResult.data?.helloWorld}")
    }

    // mutation examples
    println("simple mutation examples")
    val retrieveObjectQuery = RetrieveObjectQuery(client)
    val addObjectMutation = AddObjectMutation(client)
    val updateObjectMutation = UpdateObjectMutation(client)
    runBlocking {
        val retrieveNonExistentObject = retrieveObjectQuery.execute(variables = RetrieveObjectQuery.Variables(id = 1))
        println("\tretrieve non existent object: ${retrieveNonExistentObject.data?.retrieveBasicObject}")

        val addResult = addObjectMutation.execute(variables = AddObjectMutation.Variables(newObject = AddObjectMutation.BasicObjectInput(1, "first")))
        println("\tadd new object: ${addResult.data?.addBasicObject}")

        val updateResult = updateObjectMutation.execute(variables = UpdateObjectMutation.Variables(updatedObject = UpdateObjectMutation.BasicObjectInput(1, "updated")))
        println("\tupdate new object: ${updateResult.data?.updateBasicObject}")
    }
    client.close()
}
