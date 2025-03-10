package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.UserJpaRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import com.group.libraryapp.dto.book.response.BookStatResponse
import com.group.libraryapp.repository.book.BookQuerydslRepository
import com.group.libraryapp.repository.user.loanhistory.UserLoanHistoryQuerydslRepository
import com.group.libraryapp.util.fail
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService(
  private val bookRepository: BookRepository,
  private val bookQuerydslRepository: BookQuerydslRepository,
  private val userJpaRepository: UserJpaRepository,
  private val userLoanHistoryRepository: UserLoanHistoryQuerydslRepository,
) {

  @Transactional
  fun saveBook(request: BookRequest) {
    val book = Book(request.name, request.type)
    bookRepository.save(book)
  }

  @Transactional
  fun loanBook(request: BookLoanRequest) {

    val book = bookRepository.findByName(request.bookName) ?: fail()

    if (userLoanHistoryRepository.find(request.bookName, UserLoanStatus.LOANED) != null) {
      throw IllegalArgumentException("진작 대출되어 있는 책입니다")
    }

    val user = userJpaRepository.findByName(request.userName) ?: fail()
    user.loanBook(book)

  }

  @Transactional
  fun returnBook(request: BookReturnRequest) {
    val user = userJpaRepository.findByName(request.userName) ?: fail()
    user.returnBook(request.bookName)
  }

  @Transactional(readOnly = true)
  fun countLoanedBook(): Int {
    return userLoanHistoryRepository.count(UserLoanStatus.LOANED).toInt()
  }

  @Transactional(readOnly = true)
  fun getBookStatistics(): List<BookStatResponse> {
    return bookQuerydslRepository.getStats()
  }


}