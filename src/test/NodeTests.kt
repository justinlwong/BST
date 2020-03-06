package test

import main.Node
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class NodeTests {

    @Test
    fun addNodes() {
        val root = Node(10)
        root.insert(5)
        root.insert(12)
        assertEquals(5, root.left?.key)
        assertEquals(12, root.right?.key)
    }

    @Test
    fun addRemoveNodesNoChild() {
        val root = Node(10)
        root.insert(5)
        root.insert(12)
        assertEquals(5, root.left?.key)
        assertEquals(12, root.right?.key)
        root.remove(5)
        assertNull(root.left)
        assertEquals(12, root.right?.key)
    }

    @Test
    fun addRemoveNodesOneChild() {
        val root = Node(10)
        root.insert(5)
        root.insert(12)
        root.insert(3)
        assertEquals(5, root.left?.key)
        assertEquals(12, root.right?.key)
        assertEquals(3, root.left?.left?.key)
        root.remove(5)
        assertEquals(3, root.left?.key)
        assertEquals(12, root.right?.key)
    }

    @Test
    fun addRemoveNodesTwoChild() {
        val root = Node(10)
        root.insert(5)
        root.insert(12)
        root.insert(3)
        root.insert(6)
        assertEquals(5, root.left?.key)
        assertEquals(12, root.right?.key)
        assertEquals(3, root.left?.left?.key)
        assertEquals(6, root.left?.right?.key)
        root.remove(5)
        root.visit().forEach {
            println(it)
        }
        assertEquals(3, root.left?.key)
        assertEquals(6, root.left?.right?.key)
        assertEquals(12, root.right?.key)
    }

    @Test
    fun findNode() {
        val root = Node(10)
        root.insert(5)
        root.insert(12)
        root.insert(3)
        assertEquals(3, root.search(3)?.key)
        assertNull(root.search(4))
    }
}