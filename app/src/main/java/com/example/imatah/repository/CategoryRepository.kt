package com.example.imatah.repository

import com.example.imatah.model.Category
import com.example.imatah.R

object CategoryRepository {
    fun getCategories(): List<Category> {
        return listOf(
            Category(
                id = 1,
                name = "تلوث",
                description = "تقارير عن مشاكل التلوث البيئي الناجمة عن الأنشطة الصناعية والمواصلات.",
                imageUrl = R.drawable.im02
            ),
            Category(
                id = 2,
                name = "حوادث",
                description = "تقارير عن الحوادث المرورية والوقائع الطارئة.",
                imageUrl = R.drawable.im04
            ),
            Category(
                id = 3,
                name = "أضرار بيئية",
                description = "تقارير عن التلوث الناتج عن تراكم النفايات والإهمال البيئي.",
                imageUrl = R.drawable.im08
            ),
            Category(
                id = 4,
                name = "مشاكل صرف صحي",
                description = "تقارير عن تسرب مياه الصرف الصحي ومشاكل الصرف الصحي.",
                imageUrl = R.drawable.im09
            ),
            Category(
                id = 5,
                name = "البنية التحتية",
                description = "تقارير عن الأعطال في البنية التحتية والمشاكل المتعلقة بالطرق والجسور.",
                imageUrl = R.drawable.im06
            )
        )
    }
}


