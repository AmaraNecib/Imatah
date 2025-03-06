package com.example.imatah.repository

import com.example.imatah.model.Report
import com.example.imatah.R
import java.util.Date

object ReportRepository {
    fun getReports(): List<Report> {
        return listOf(
            Report(
                id = 1,
                name = "تلوث بيئي في حاسي مسعود ",
                description = "📍 انسكاب نفطي  أدى إلى تلطيخ مياه الشوارع وتلويث الهواء.",
                status = "Pending",
                category = "تلوث",
                imageUrl = R.drawable.im02,
                coordinates = Pair(36.7538, 3.0588),
                createdAt = Date(),
                updatedAt = Date()
            ),
            Report(
                id = 2,
                name = "حادث طرق في وهران",
                description = "📍 تصادم عدة سيارات على طريق رئيسي في وهران، وأسفر عن توقف حركة المرور.",
                status = "Completed",
                category = "حوادث",
                imageUrl = R.drawable.im01,
                coordinates = Pair(35.6971, -0.6308),
                createdAt = Date(),
                updatedAt = Date()
            ),
            Report(
                id = 3,
                name = "انبعاثات صناعية في قسنطينة",
                description = "📍 مصنع ينبعث منه دخان كثيف يسبب تلوثاً في الهواء بمحافظة قسنطينة.",
                status = "In Progress",
                category = "تلوث",
                imageUrl = R.drawable.im03,
                coordinates = Pair(36.3650, 6.6147),
                createdAt = Date(),
                updatedAt = Date()
            ),
            Report(
                id = 4,
                name = "تسرب مواد كيميائية في عنابة",
                description = "📍 تسرب مواد كيميائية من إحدى المنشآت الصناعية في عنابة يؤدي إلى قلق بيئي.",
                status = "Pending",
                category = "حوادث",
                imageUrl = R.drawable.im04,
                coordinates = Pair(36.9000, 7.7667),
                createdAt = Date(),
                updatedAt = Date()
            ),
            Report(
                id = 5,
                name = "تراكم نفايات في البليدة",
                description = "📍 وجود أكوام نفايات غير معالجة في أحد الأحياء يؤدي إلى تلوث محلي وانتشار الحشرات.",
                status = "Completed",
                category = "أضرار بيئية",
                imageUrl = R.drawable.im05,
                coordinates = Pair(36.4700, 2.8300),
                createdAt = Date(),
                updatedAt = Date()
            ),
            Report(
                id = 6,
                name = "مشاكل صرف صحي في تيزي وزو",
                description = "📍 تسرب مياه الصرف الصحي في أحد الأحياء السكنية بتيزي وزو يسبب روائح كريهة وتلوث.",
                status = "In Progress",
                category = "مشاكل صرف صحي",
                imageUrl = R.drawable.im06,
                coordinates = Pair(36.7111, 4.0481),
                createdAt = Date(),
                updatedAt = Date()
            ),
            Report(
                id = 7,
                name = "حادث مروري في باتنة",
                description = "📍 حادث سير أدى إلى تضرر المركبات وتوقف حركة المرور في شارع رئيسي بباتنة.",
                status = "Pending",
                category = "حوادث",
                imageUrl = R.drawable.im07,
                coordinates = Pair(35.5550, 6.1740),
                createdAt = Date(),
                updatedAt = Date()
            ),
            Report(
                id = 8,
                name = "أضرار في البنية التحتية بسطيف",
                description = "📍 جسر متصدع في سطيف يعوق حركة المرور ويتسبب بخطورة الاستخدام.",
                status = "Completed",
                category = "البنية التحتية",
                imageUrl = R.drawable.im08,
                coordinates = Pair(36.1911, 5.4133),
                createdAt = Date(),
                updatedAt = Date()
            ),
            Report(
                id = 9,
                name = "تلوث ناتج عن المصانع في بجاية",
                description = "📍 انبعاثات ملوثة من المصانع تلوث الهواء وتؤثر على صحة السكان في بجاية.",
                status = "In Progress",
                category = "تلوث",
                imageUrl = R.drawable.im06,
                coordinates = Pair(36.7500, 5.0833),
                createdAt = Date(),
                updatedAt = Date()
            ),
            Report(
                id = 10,
                name = "مشاكل بنية تحتية في مستغانم",
                description = "📍 حرائق  أدى إلى تلف أجزاء من البنية التحتية في مستغانم.",
                status = "Pending",
                category = "البنية التحتية",
                imageUrl = R.drawable.im10,
                coordinates = Pair(35.9333, 0.0833),
                createdAt = Date(),
                updatedAt = Date()
            )
        )
    }
}
