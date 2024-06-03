package com.example.libraryReservationKotlin.common.pageable

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

class CustomPageRequest(
    private val page: Int = 0,
    private val size: Int = 10,
    private val sort: Sort = Sort.by("id").ascending(),
) : Pageable {
    override fun getPageNumber(): Int = page

    override fun getPageSize(): Int = size

    override fun getOffset(): Long = (page * size).toLong()

    override fun getSort(): Sort = sort

    override fun next(): Pageable {
        return CustomPageRequest(page + 1, size, sort)
    }

    override fun previousOrFirst(): Pageable {
        return if (page == 0) this else CustomPageRequest(page - 1, size, sort)
    }

    override fun first(): Pageable {
        return CustomPageRequest(0, size, sort)
    }

    override fun withPage(pageNumber: Int): Pageable {
        return CustomPageRequest(pageNumber, size, sort)
    }

    override fun hasPrevious(): Boolean {
        return page > 0
    }
}
