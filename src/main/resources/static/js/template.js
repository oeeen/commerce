const template = (function () {
    const user = `<tr>
                            <td>
                                <img src="https://bootdey.com/img/Content/avatar/avatar1.png" alt="">
                                <a href="#" class="user-link">{{username}}</a>
                                <span class="user-subhead">{{authority}}</span>
                            </td>
                            <td>
                                {{nickname}}
                            </td>
                            <td class="text-center">
                                <span class="label label-default">Inactive</span>
                            </td>
                            <td>
                                <a href="#">{{email}}</a>
                            </td>
                            <td style="width: 20%;">
                                <a href="#" class="table-link">
                                    <span class="fa-stack">
                                        <i class="fa fa-square fa-stack-2x"></i>
                                        <i class="fa fa-search-plus fa-stack-1x fa-inverse"></i>
                                    </span>
                                </a>
                                <a href="#" class="table-link">
                                    <span class="fa-stack">
                                        <i class="fa fa-square fa-stack-2x"></i>
                                        <i class="fa fa-pencil fa-stack-1x fa-inverse"></i>
                                    </span>
                                </a>
                                <a href="#" class="table-link danger">
                                    <span class="fa-stack">
                                        <i class="fa fa-square fa-stack-2x"></i>
                                        <i class="fa fa-trash-o fa-stack-1x fa-inverse"></i>
                                    </span>
                                </a>
                            </td>
                        </tr>`

    return {
        user: user,
    }
})();