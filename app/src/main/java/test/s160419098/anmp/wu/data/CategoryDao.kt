package test.s160419098.anmp.wu.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface CategoryDao {
    @Query(
        """
            SELECT *
            FROM categories
            JOIN menus ON menus.category_id = categories.id
            WHERE
                categories.name LIKE '%' || :query || '%'
                OR
                menus.name LIKE '%' || :query || '%'
        """
    )
    fun query(query: String): Map<Category, List<Menu>>
}
