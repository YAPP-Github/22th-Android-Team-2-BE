package com.yapp.bol.group

import com.yapp.bol.group.dto.AddGuestDto
import com.yapp.bol.group.dto.CreateGroupDto
import com.yapp.bol.group.dto.GroupMemberList
import com.yapp.bol.group.dto.GroupWithMemberCount
import com.yapp.bol.group.dto.JoinGroupDto
import com.yapp.bol.pagination.offset.PaginationCursor

interface GroupService {
    fun createGroup(
        createGroupDto: CreateGroupDto
    ): GroupMemberList

    fun joinGroup(request: JoinGroupDto)

    fun searchGroup(
        name: String?,
        pageNumber: Int,
        pageSize: Int
    ): PaginationCursor<GroupWithMemberCount>

    fun addGuest(request: AddGuestDto)
}
