package com.ritier.springr2dbcsample.posting.adapter.out.persistence.converter

import com.ritier.springr2dbcsample.shared.exception.AppException
import com.ritier.springr2dbcsample.shared.exception.ErrorCode
import com.ritier.springr2dbcsample.shared.util.ConverterUtil
import com.ritier.springr2dbcsample.posting.domain.model.Posting
import com.ritier.springr2dbcsample.posting.domain.vo.PostingId
import com.ritier.springr2dbcsample.user.domain.vo.UserId
import org.springframework.stereotype.Component

@Component
object PostingRowConverter {

    fun convertFromRow(row: Map<String, *>): Posting {
        val postingId = row["posting_id"]?.toString()?.toLongOrNull()
            ?: throw AppException(ErrorCode.POSTING_DATA_CONVERSION_ERROR)
        val userId = row["user_id"]?.toString()?.toLongOrNull()
            ?: throw AppException(ErrorCode.POSTING_DATA_CONVERSION_ERROR)
        val contents = row["posting_contents"]?.toString()
            ?: throw AppException(ErrorCode.POSTING_DATA_CONVERSION_ERROR)
        val createdAtStr = row["posting_created_at"]?.toString()
            ?: throw AppException(ErrorCode.POSTING_DATA_CONVERSION_ERROR)

        return Posting(
            id = PostingId(postingId),
            userId = UserId(userId),
            contents = contents,
            createdAt = ConverterUtil.convertStrToLocalDateTime(createdAtStr)
        )
    }
}
