# Kotlin-ChainingMethods
Chaining methods with kotlin generics

## Usage
```kotlin
some method ==> another method ==> some third methods

(::someMethod ß ::anotherMethod)(params) { result ->
  result.onSuccess { thing ->
    Log.d("Chaining", "Chain result success: $thing")
  }
  result.onFailure { error ->
    Log.d("Chaining", "Chain result failure" + error.localizedMessage?.toString())
  }
}
```

Methods must have input parameter and completion with ChainOperationCompletion type.
