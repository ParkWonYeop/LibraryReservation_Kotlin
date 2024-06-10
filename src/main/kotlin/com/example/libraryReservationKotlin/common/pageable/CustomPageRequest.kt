package com.example.libraryReservationKotlin.common.pageable

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

class CustomPageRequest(
    private val page: Int = 0,
    private val size: Int = 10,
    private val sort: Sort = Sort.by("id").ascending(),
) : Pageable {
    override fun getPageNumber() = page

    override fun getPageSize() = size

    override fun getOffset() = (page * size).toLong()

    override fun getSort() = sort

    override fun next() = CustomPageRequest(page + 1, size, sort)

    override fun previousOrFirst() = if (page == 0) {
        this
    } else {
        CustomPageRequest(page - 1, size, sort)
    }

    override fun first() = CustomPageRequest(0, size, sort)

    override fun withPage(
        pageNumber: Int,
    ) = CustomPageRequest(pageNumber, size, sort)

    override fun hasPrevious() = page > 0
}
