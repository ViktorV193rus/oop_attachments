data class Post(
    val id: Int,
    val authorId: Long,
    val authorName: String,
    val content: String,
    val published: Long,
    val date: Int,
    val repost: String,
    val search: String,
    val checkCopyrightLink: String,
    var likes: Likes,
    var attachments: Array<Attachments> = emptyArray()
) {

}

data class Comment(
    val count: Long,
    val text: String,
    val id: Int,
    val canPost: Boolean,
    val groupsCanPost: Boolean,
    val canClose: Boolean,
    val canOpen: Boolean
) {

}

class Likes(likes: Int) {
    var likes = likes
        set(value) {
            if (value < 0) {
                return
            }
            field = value
        }
}

object WallService {
    private var posts = emptyArray<Post>()
    private var standartPostId: Int = 1
    private var standartCommentID: Int = 1
    private var comments = emptyArray<Comment>()

    fun add(post: Post): Post {
        posts += post.copy(id = standartPostId)
        standartPostId++
        return posts.last()
    }

    fun update(post: Post): Boolean {
        for ((index, item) in posts.withIndex()) {
            if (item.id == post.id) {
                posts[index] = post.copy(id = post.id, date = post.date)
                return true
            }
        }
        return false
    }

    fun newComment(comment: Comment, postId: Int): Comment {
        for (post in posts) {
            if (post.id == postId) {
                standartCommentID++
                val newComment = comment.copy(id = standartCommentID)
                comments += newComment
                //println("Комментарий добавлен")
                return comments.last()
            }
        }
        throw NoPost("Пост удален или не существует")
    }

    fun printPost() {
        for (post in posts) {
            println(post)
        }
        println()
    }
}


fun main() {
    WallService.add(
        post = Post(
            1, 100, "Ivan", "lala", 5, 2021, "Nina", "",
            "", Likes(0), attachments = arrayOf(VideoAttachments(Video(1, "ala")))
        )
    )
    WallService.newComment(Comment(1, "good", 1,
        canPost = false,
        groupsCanPost = false,
        canClose = false,
        canOpen = false
    ),1)

    WallService.printPost()

}

