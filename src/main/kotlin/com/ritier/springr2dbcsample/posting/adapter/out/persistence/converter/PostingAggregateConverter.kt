package com.ritier.springr2dbcsample.posting.adapter.out.persistence.converter

import com.ritier.springr2dbcsample.shared.exception.AppException
import com.ritier.springr2dbcsample.shared.exception.ErrorCode
import com.ritier.springr2dbcsample.user.adapter.out.persistence.converter.UserRowConverter
import com.ritier.springr2dbcsample.posting.domain.model.Posting
import org.springframework.stereotype.Component

// 집계만 담당하는 컨버터
@Component
class PostingAggregateConverter {

    fun convertRowListToPosting(rowList: List<Map<String, *>>): Posting {
        validateRowList(rowList)

        val firstRow = rowList.first()

        // 각 컨버터에 책임 위임
        val basicPosting = PostingRowConverter.convertFromRow(firstRow)
        val user = UserRowConverter.convertFromRow(firstRow)
        val images = PostingImageRowConverter.convertFromRows(rowList)

        return basicPosting.copy(
            user = user,
            images = images
        )
    }

    private fun validateRowList(rowList: List<Map<String, *>>) {
        if (rowList.isEmpty()) {
            throw AppException(ErrorCode.POSTING_DATA_CONVERSION_ERROR)
        }
    }
}
