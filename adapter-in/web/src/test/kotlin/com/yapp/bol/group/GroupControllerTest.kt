package com.yapp.bol.group

import com.yapp.bol.base.ControllerTest
import com.yapp.bol.base.NUMBER
import com.yapp.bol.base.OpenApiTag
import com.yapp.bol.base.STRING
import com.yapp.bol.group.dto.CreateGroupRequest
import com.yapp.bol.group.member.Member
import com.yapp.bol.group.member.MemberId
import com.yapp.bol.group.member.MemberList
import com.yapp.bol.group.member.MemberRole
import io.mockk.every
import io.mockk.mockk

class GroupControllerTest : ControllerTest() {
    private val groupService: GroupService = mockk()
    override val controller = GroupController(groupService)

    init {
        test("POST /v1/group") {
            val request = CreateGroupRequest(
                name = "뽀글뽀글",
                description = "보겜동입니다",
                organization = "카카오",
                profileImageUrl = "https://profile.com",
                nickname = "홀든",
            )

            val group = Group(
                id = GroupId(1),
                name = "뽀글뽀글",
                description = "보겜동입니다",
                organization = "카카오",
                profileImageUrl = "https://profile.com",
                accessCode = "1A2B3C",
            )

            val members = MemberList.of(
                Member(
                    id = MemberId(123),
                    nickname = "홀든",
                    groupId = group.id,
                    role = MemberRole.OWNER
                )
            )

            every {
                groupService.createGroup(any())
            } returns GroupMemberList(group = group, members = members)

            post("/v1/group", request) {}
                .isStatus(200)
                .makeDocument(
                    DocumentInfo(identifier = "group", tag = OpenApiTag.GROUP),
                    requestFields(
                        "name" type STRING means "그룹 이름",
                        "description" type STRING means "그룹 설명",
                        "organization" type STRING means "그룹 소속",
                        "profileImageUrl" type STRING means "그룹 프로필 이미지 URL" isOptional true,
                        "nickname" type STRING means "그룹장 닉네임" isOptional true,
                    ),
                    responseFields(
                        "id" type NUMBER means "그룹 ID",
                        "name" type STRING means "그룹 이름",
                        "description" type STRING means "그룹 설명",
                        "owner" type STRING means "그룹장 닉네임",
                        "organization" type STRING means "그룹 소속",
                        "profileImageUrl" type STRING means "그룹 프로필 이미지 URL",
                        "accessCode" type STRING means "그룹 접근 코드"
                    )
                )
        }
    }
}